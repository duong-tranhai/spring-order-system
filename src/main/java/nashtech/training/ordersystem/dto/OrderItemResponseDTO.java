package nashtech.training.ordersystem.dto;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        int quantity,
        double price
){}