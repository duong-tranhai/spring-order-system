package nashtech.training.emailservice.listener;

import lombok.RequiredArgsConstructor;
import nashtech.training.emailservice.config.RabbitMQConfig;
import nashtech.training.emailservice.event.OrderEmailEvent;
import nashtech.training.emailservice.service.EmailNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailListener {

    private final EmailNotificationService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveEmailEvent(OrderEmailEvent event) {ss
        Map<String, Object> variables = Map.of(
                "orderId", event.getOrderId(),
                "status", event.getStatus()
        );

        emailService.sendOrderStatusEmail(
                event.getTo(),
                event.getSubject(),
                variables
        );
    }
}

