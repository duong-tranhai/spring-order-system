package nashtech.training.ordersystem.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.succeeded}")
    private String succeededQueueName;

    @Value("${rabbitmq.queue.failed}")
    private String failedQueueName;

    @Value("${rabbitmq.routingkey.succeeded}")
    private String succeededRoutingKey;

    @Value("${rabbitmq.routingkey.failed}")
    private String failedRoutingKey;

    // This configuration declares the exchange, the queues, and the bindings between them.
    // It also provides a JSON message converter.

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
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
        return BindingBuilder.bind(succeededQueue()).to(exchange()).with(succeededRoutingKey);
    }

    @Bean
    public Binding failedBinding() {
        return BindingBuilder.bind(failedQueue()).to(exchange()).with(failedRoutingKey);
    }

    // Use JSON for serializing/deserializing messages between services.
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}