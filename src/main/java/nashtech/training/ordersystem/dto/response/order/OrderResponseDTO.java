package nashtech.training.ordersystem.dto.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String customerUsername,
        String sellerUsername,
        String status,
        String shippingAddress,
        String paymentStatus,
        BigDecimal totalAmount,
        List<OrderItemResponseDTO> items,
        LocalDateTime orderDate
) {}
