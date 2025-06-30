package nashtech.training.ordersystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import nashtech.training.ordersystem.event.PaymentStatusChangedEvent;
import nashtech.training.ordersystem.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
@Slf4j
@Service
public class PaymentEventListener {
    private final OrderService orderService;

    public PaymentEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "payment.succeeded.queue")
    public void handlePaymentSucceeded(PaymentStatusChangedEvent event) {
        log.info("✅ Payment succeeded for orderId: {}", event.orderId());
        orderService.markOrderAsPaid(event.orderId());
    }

    @RabbitListener(queues = "payment.failed.queue")
    public void handlePaymentFailed(PaymentStatusChangedEvent event) {
        log.warn("❌ Payment failed for orderId: {}. Reason: {}", event.orderId(), event.failureMessage());
        orderService.markOrderAsPaymentFailed(event.orderId());
    }
}
