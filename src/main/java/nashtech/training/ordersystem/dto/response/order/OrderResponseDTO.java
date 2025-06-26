package nashtech.training.ordersystem.dto.response.order;

import nashtech.training.ordersystem.dto.response.payment.PaymentResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String customerUsername,
        String sellerUsername,
        String status,
        String shippingAddress,
        List<PaymentResponseDTO> payments,
        BigDecimal totalAmount,
        List<OrderItemResponseDTO> items,
        LocalDateTime orderDate
) {}
