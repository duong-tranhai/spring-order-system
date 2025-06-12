package nashtech.training.ordersystem.dto;

public record OrderItemRequestDTO(
        Long productId,
        int quantity
) {}
