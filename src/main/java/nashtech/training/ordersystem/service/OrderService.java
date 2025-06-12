package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.OrderRequestDTO;
import nashtech.training.ordersystem.dto.OrderResponseDTO;
import nashtech.training.ordersystem.entity.OrderStatus;
import nashtech.training.ordersystem.entity.Role;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(String customerUsername, OrderRequestDTO orderRequestDTO);
    List<OrderResponseDTO> getOrdersForUser(String username, Role role);
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus);
}