package nashtech.training.ordersystem.dto.request.order;

import nashtech.training.ordersystem.entity.PaymentMethod;

import java.util.List;

public record CreateOrderDTO(
        String shippingAddress,
        PaymentMethod paymentMethod,
        String username,
        List<OrderItemRequestDTO> items
) {}
