package nashtech.training.ordersystem.dto.request.payment;

public record PaymentRequestDTO(
        Long orderId,
        Long amount,
        String currency
) {}
