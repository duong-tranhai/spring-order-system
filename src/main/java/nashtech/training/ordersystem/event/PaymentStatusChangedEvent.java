package nashtech.training.ordersystem.event;


public record PaymentStatusChangedEvent(
        String paymentIntentId,
        String orderId,
        String status,
        Long amount,
        String currency,
        String failureMessage
) {}

