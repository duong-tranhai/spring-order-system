package nashtech.training.ordersystem.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENT_EXCHANGE = "payment.exchange";

    public static final String PAYMENT_SUCCESS_QUEUE = "payment.succeeded.queue";
    public static final String PAYMENT_FAILED_QUEUE = "payment.failed.queue";

    public static final String PAYMENT_SUCCESS_ROUTING_KEY = "payment.succeeded.routingkey";
    public static final String PAYMENT_FAILED_ROUTING_KEY = "payment.failed.routingkey";

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(PAYMENT_EXCHANGE);
    }

    @Bean
    public Queue paymentSucceededQueue() {
        return new Queue(PAYMENT_SUCCESS_QUEUE);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(PAYMENT_FAILED_QUEUE);
    }

    @Bean
    public Binding bindingSucceededQueue(Queue paymentSucceededQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentSucceededQueue)
                .to(paymentExchange)
                .with(PAYMENT_SUCCESS_ROUTING_KEY);
    }

    @Bean
    public Binding bindingFailedQueue(Queue paymentFailedQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentFailedQueue)
                .to(paymentExchange)
                .with(PAYMENT_FAILED_ROUTING_KEY);
    }
}
