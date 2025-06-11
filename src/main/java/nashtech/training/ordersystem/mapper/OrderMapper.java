package nashtech.training.ordersystem.mapper;

import nashtech.training.ordersystem.dto.response.order.OrderItemResponseDTO;
import nashtech.training.ordersystem.dto.response.order.OrderResponseDTO;
import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.username", target = "customerUsername")
    @Mapping(target = "sellerUsername", expression = "java(order.getSeller() != null ? order.getSeller().getUsername() : null)")
    @Mapping(source = "orderItems", target = "items")
    OrderResponseDTO toOrderDto(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDTO toOrderItemDto(OrderItem item);
}
