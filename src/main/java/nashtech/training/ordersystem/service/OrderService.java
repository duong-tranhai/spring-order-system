package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.*;
import nashtech.training.ordersystem.entity.RoleName;
import nashtech.training.ordersystem.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(String customerUsername, OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getOrdersForUser(String username, RoleName roleName);
    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus);
    void requestReturn(Long orderId, String username);
    void approveReturn(Long orderId);
}
