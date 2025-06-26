package nashtech.training.ordersystem.dto.response.payment;

import nashtech.training.ordersystem.entity.OrderPaymentStatus;
import nashtech.training.ordersystem.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(
        PaymentMethod method,
        OrderPaymentStatus status,
        BigDecimal amount,
        LocalDateTime paymentDate
) {}
