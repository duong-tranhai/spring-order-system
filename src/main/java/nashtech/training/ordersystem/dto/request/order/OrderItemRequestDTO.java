package nashtech.training.ordersystem.dto.request.order;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
        Long productId,
        Integer quantity,
        BigDecimal voucherDiscount
) {}
