package nashtech.training.ordersystem.listener;

import nashtech.training.common.dto.PaymentStatusChangedEvent;
import nashtech.training.ordersystem.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(PaymentEventListener.class);

    public PaymentEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    // This method will automatically be called when a message arrives in the queue
    @RabbitListener(queues = "payment.succeeded.queue")
    public void handlePaymentSucceeded(PaymentStatusChangedEvent event) {
        logger.info("Received Payment Succeeded Event for orderId: {}", event.orderId());

        // Find the order and update its status
        orderService.markOrderAsPaid(Long.parseLong(event.orderId()));

        // You could also trigger sending a confirmation email from here
        // emailService.sendOrderConfirmation(event.orderId());
    }

    @RabbitListener(queues = "payment.failed.queue")
    public void handlePaymentFailed(PaymentStatusChangedEvent event) {
        logger.warn("Received Payment Failed Event for orderId: {}. Reason: {}", event.orderId(), event.failureMessage());

        // Find the order and update its status to reflect the failure
        orderService.markOrderAsPaymentFailed(Long.parseLong(event.orderId()));
    }
}
