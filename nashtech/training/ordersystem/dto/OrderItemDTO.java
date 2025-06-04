package nashtech.training.ordersystem.dto;

public record OrderItemDTO(
        Long productId,
        int quantity
){}