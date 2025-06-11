package nashtech.training.ordersystem.dto.request.order;

import java.util.List;

public record CreateOrderDTO(
        String shippingAddress,
        String paymentStatus,
        String username,
        List<OrderItemRequestDTO> items
) {}
