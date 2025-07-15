package nashtech.training.emailservice.config;

import lombok.RequiredArgsConstructor;
import nashtech.training.emailservice.dto.EmailEvent;
import nashtech.training.emailservice.service.impl.EmailServiceImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "email.queue";
    private final EmailServiceImpl emailService;
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true); // durable queue
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmailEvent(EmailEvent event) {
        emailService.sendEmail(event);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange("email.exchange");
    }

    @Bean
    public Binding binding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("email.send");
    }
}

