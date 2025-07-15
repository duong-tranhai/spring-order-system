package nashtech.training.ordersystem.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class PaymentClient {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(PaymentClient.class);

    public PaymentClient(WebClient.Builder webClientBuilder, @Value("${payment.service.url}") String paymentServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(paymentServiceUrl).build();
    }

    public Mono<String> createPaymentIntent(String orderId, Long amount, String currency) {
        logger.info("Requesting PaymentIntent for orderId: {}", orderId);
        Map<String, Object> requestBody = Map.of(
                "orderId", orderId,
                "amount", amount,
                "currency", currency
        );

        return webClient.post()
                .uri("/api/payments/create-payment-intent")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(responseNode -> responseNode.get("clientSecret").asText())
                .doOnError(e -> logger.error("Failed to create PaymentIntent", e));
    }
}
