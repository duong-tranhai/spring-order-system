package nashtech.training.emailservice.listener;

import nashtech.training.emailservice.dto.EmailEvent;
import nashtech.training.emailservice.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailQueueListener {

    private final EmailServiceImpl emailService;

    @RabbitListener(queues = "email.queue")
    public void handleEmailEvent(EmailEvent event) {
        emailService.sendEmail(event);
    }
}
