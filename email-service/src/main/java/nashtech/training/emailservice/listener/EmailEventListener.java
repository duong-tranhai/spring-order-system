package nashtech.training.emailservice.listener;

import lombok.RequiredArgsConstructor;
import nashtech.training.common.dto.OrderEmailEvent;
import nashtech.training.common.util.OrderSystemConstants;
import nashtech.training.emailservice.service.EmailNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailNotificationService emailService;

    @RabbitListener(queues = OrderSystemConstants.RABBITMQ_EMAIL_QUEUE)
    public void handleOrderEmailNotification(OrderEmailEvent event) {
        Map<String, Object> variables = Map.of(
                "customerName", event.getReceiver(),
                "message", "Good",
                "orderStatus", event.getStatus()
        );

        emailService.sendOrderStatusEmail(
                event.getReceiver(),
                event.getSubject(),
                variables
        );
    }
}

