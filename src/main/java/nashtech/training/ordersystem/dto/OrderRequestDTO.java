package nashtech.training.ordersystem.dto;

import java.util.List;

public record OrderRequestDTO(
        List<OrderItemDTO> items
){}