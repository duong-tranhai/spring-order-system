package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.order.CreateOrderDTO;
import nashtech.training.ordersystem.dto.request.order.UpdateOrderDTO;
import nashtech.training.ordersystem.dto.response.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO getById(Long id);
    List<OrderResponseDTO> getAll();
    OrderResponseDTO createOrder(CreateOrderDTO requestDTO);
    OrderResponseDTO updateOrder(Long id, UpdateOrderDTO requestDTO);
}
