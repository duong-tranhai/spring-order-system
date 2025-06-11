package nashtech.training.ordersystem.dto.response.order;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {}
