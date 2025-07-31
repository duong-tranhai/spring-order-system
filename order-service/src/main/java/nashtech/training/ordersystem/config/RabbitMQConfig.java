package nashtech.training.ordersystem.config;

import nashtech.training.common.util.OrderSystemConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Payment Exchange Bean
    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(OrderSystemConstants.RABBITMQ_PAYMENT_EXCHANGE);
    }

    @Bean
    public Queue succeededQueue() {
        return new Queue(OrderSystemConstants.RABBITMQ_PAYMENT_QUEUE_SUCCEEDED);
    }

    @Bean
    public Queue failedQueue() {
        return new Queue(OrderSystemConstants.RABBITMQ_PAYMENT_QUEUE_FAILED);
    }

    @Bean
    public Binding succeededBinding() {
        return BindingBuilder.bind(succeededQueue()).to(paymentExchange()).with(OrderSystemConstants.RABBITMQ_PAYMENT_ROUTINGKEY_SUCCEEDED);
    }

    @Bean
    public Binding failedBinding() {
        return BindingBuilder.bind(failedQueue()).to(paymentExchange()).with(OrderSystemConstants.RABBITMQ_PAYMENT_ROUTINGKEY_FAILED);
    }

    // Email Exchange Bean
    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(OrderSystemConstants.RABBITMQ_EMAIL_EXCHANGE);
    }

    // Shared JSON Message Converter
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
