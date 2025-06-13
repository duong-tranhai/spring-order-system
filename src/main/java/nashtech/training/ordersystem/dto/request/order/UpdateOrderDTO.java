package nashtech.training.ordersystem.dto.request.order;

import java.util.List;

public record UpdateOrderDTO(
        String shippingAddress,
        String paymentStatus,
        List<OrderItemRequestDTO> items
) {
}
