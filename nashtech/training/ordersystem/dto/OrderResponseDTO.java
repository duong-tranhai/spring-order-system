package nashtech.training.ordersystem.dto;

import java.time.LocalDateTime;
import java.util.List;


public record OrderResponseDTO(
        Long id,
        String customerUsername,
        String sellerUsername,
        String status,
        List<OrderItemResponseDTO> items,
        LocalDateTime createdAt
){}