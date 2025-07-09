package nashtech.training.payment.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import nashtech.training.payment.dto.request.CreatePaymentRequest;
import nashtech.training.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    /**
     * Creates a PaymentIntent with Stripe.
     *
     * @param paymentRequest@return The created PaymentIntent.
     * @throws StripeException if there's an error interacting with the Stripe API.
     */
    @Override
    public PaymentIntent createPaymentIntent(CreatePaymentRequest paymentRequest) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentRequest.amount())
                        .setCurrency(paymentRequest.currency())
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter
                        // is optional because Stripe enables its functionality by default.
                        .putMetadata("order_id", String.valueOf(paymentRequest.orderId())) // Optional: Add metadata
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                        )
                        .build();

        return PaymentIntent.create(params);
    }
}
