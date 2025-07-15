package nashtech.training.payment.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange name
    public static final String PAYMENT_EXCHANGE = "payment.exchange";

    // Routing keys
    public static final String PAYMENT_SUCCESS_ROUTING_KEY = "payment.succeeded.routingkey";
    public static final String PAYMENT_FAILED_ROUTING_KEY = "payment.failed.routingkey";

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(PAYMENT_EXCHANGE);
    }
}

