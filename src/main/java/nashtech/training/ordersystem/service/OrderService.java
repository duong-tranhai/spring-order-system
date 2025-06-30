package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.order.CreateOrderDTO;
import nashtech.training.ordersystem.dto.request.order.OrderSearchFilter;
import nashtech.training.ordersystem.dto.request.order.UpdateOrderDTO;
import nashtech.training.ordersystem.dto.request.payment.PaymentRequestDTO;
import nashtech.training.ordersystem.dto.request.user.UserSearchFilter;
import nashtech.training.ordersystem.dto.response.order.OrderResponseDTO;
import nashtech.training.ordersystem.dto.response.user.UserResponseDTO;
import nashtech.training.ordersystem.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDTO getById(Long id);
    List<OrderResponseDTO> getAll();
    List<OrderResponseDTO> getAllWithFilters(OrderSearchFilter filter);
    OrderResponseDTO createOrder(CreateOrderDTO requestDTO);
    OrderResponseDTO updateOrder(Long id, UpdateOrderDTO requestDTO);
    OrderResponseDTO changeStatusOrder(Long orderId, OrderStatus newStatus);
    void initiatePayment(PaymentRequestDTO request);
    void markOrderAsPaid(String orderId);
    void markOrderAsPaymentFailed(String orderId);
}
