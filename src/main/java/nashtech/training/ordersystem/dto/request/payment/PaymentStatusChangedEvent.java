package nashtech.training.ordersystem.dto.request.payment;

public record PaymentStatusChangedEvent(
        String paymentIntentId,
        String orderId, // Crucial for the Order Service to know which order to update
        String status, // e.g., "SUCCEEDED", "FAILED"
        Long amount,
        String currency,
        String failureMessage // Null if succeeded
) {}