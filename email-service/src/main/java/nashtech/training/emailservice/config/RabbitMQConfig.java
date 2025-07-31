package nashtech.training.emailservice.config;

import nashtech.training.common.util.OrderSystemConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(OrderSystemConstants.RABBITMQ_EMAIL_EXCHANGE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(OrderSystemConstants.RABBITMQ_EMAIL_QUEUE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(OrderSystemConstants.RABBITMQ_EMAIL_ROUTINGKEY);
    }
}
