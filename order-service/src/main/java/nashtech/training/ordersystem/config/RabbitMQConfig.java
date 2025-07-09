package nashtech.training.ordersystem.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Payment Exchange
    @Value("${rabbitmq.exchange.name}")
    private String paymentExchangeName;

    @Value("${rabbitmq.queue.succeeded}")
    private String succeededQueueName;

    @Value("${rabbitmq.queue.failed}")
    private String failedQueueName;

    @Value("${rabbitmq.routingkey.succeeded}")
    private String succeededRoutingKey;

    @Value("${rabbitmq.routingkey.failed}")
    private String failedRoutingKey;

    // Email Exchange
    @Value("${rabbitmq.email.exchange}")
    private String emailExchangeName;

    @Value("${rabbitmq.queue.email}")
    private String emailQueueName;

    @Value("${rabbitmq.routingkey.email}")
    private String emailRoutingKey;

    // Payment Exchange Bean
    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(paymentExchangeName);
    }

    @Bean
    public Queue succeededQueue() {
        return new Queue(succeededQueueName);
    }

    @Bean
    public Queue failedQueue() {
        return new Queue(failedQueueName);
    }

    @Bean
    public Binding succeededBinding() {
        return BindingBuilder.bind(succeededQueue()).to(paymentExchange()).with(succeededRoutingKey);
    }

    @Bean
    public Binding failedBinding() {
        return BindingBuilder.bind(failedQueue()).to(paymentExchange()).with(failedRoutingKey);
    }

    // Email Exchange Bean
    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(emailExchangeName);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(emailRoutingKey);
    }

    // Shared JSON Message Converter
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
