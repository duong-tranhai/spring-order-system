package nashtech.training.ordersystem.dto.request.order;

public record OrderItemRequestDTO(
        Long productId,
        Integer quantity
) {}
