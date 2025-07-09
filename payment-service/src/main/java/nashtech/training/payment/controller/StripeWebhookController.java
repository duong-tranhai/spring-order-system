package nashtech.training.payment.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import nashtech.training.common.dto.PaymentStatusChangedEvent;
import nashtech.training.payment.dto.request.CreatePaymentRequest;
import nashtech.training.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class StripeWebhookController {

    private final RabbitTemplate rabbitTemplate;
    private final String webhookSecret;
    private final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);
    private final PaymentService paymentService;

    // Constructor injection for RabbitTemplate and the Stripe webhook secret
    public StripeWebhookController(RabbitTemplate rabbitTemplate,
                                   @Value("${stripe.webhook.secret}") String webhookSecret,PaymentService paymentService) {
        this.rabbitTemplate = rabbitTemplate;
        this.webhookSecret = webhookSecret;
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment-intent")
    public Map<String, String> createPaymentIntent(@RequestBody CreatePaymentRequest request) throws StripeException {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(request);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());

            return response;
        } catch (StripeException e) {
            System.err.println("Stripe error: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            logger.warn("⚠️ Webhook signature verification failed.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed.");
        }

        StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

        switch (event.getType()) {
            case "payment_intent.succeeded":
                logger.info("✅ Payment for {} succeeded!","1" );

                PaymentStatusChangedEvent successEvent = new PaymentStatusChangedEvent(
                        "id",
                        "1",
                        "SUCCEEDED",
                        2000L,
                        "usd",
                        null
                );

                rabbitTemplate.convertAndSend("payment.exchange", "payment.succeeded.routingkey", successEvent);
                break;

            case "payment_intent.payment_failed":
                PaymentIntent failedPaymentIntent = (PaymentIntent) stripeObject;
                logger.error("❌ Payment for {} failed", failedPaymentIntent.getId());

                PaymentStatusChangedEvent failedEvent = new PaymentStatusChangedEvent(
                        failedPaymentIntent.getId(),
                        failedPaymentIntent.getMetadata().get("order_id"),
                        "FAILED",
                        failedPaymentIntent.getAmount(),
                        failedPaymentIntent.getCurrency(),
                        failedPaymentIntent.getLastPaymentError().getMessage()
                );

                rabbitTemplate.convertAndSend("payment.exchange", "payment.failed.routingkey", failedEvent);
                break;

            default:
                logger.warn("Unhandled event type: {}", event.getType());
        }

        return ResponseEntity.ok("Success");
    }
}
