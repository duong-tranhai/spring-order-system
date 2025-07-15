package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.order.CreateOrderDTO;
import nashtech.training.ordersystem.dto.request.order.UpdateOrderDTO;
import nashtech.training.ordersystem.dto.request.payment.PaymentRequestDTO;
import nashtech.training.ordersystem.dto.response.order.OrderResponseDTO;
import nashtech.training.ordersystem.entity.OrderStatus;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {
    OrderResponseDTO getById(Long id);
    List<OrderResponseDTO> getAll();
    OrderResponseDTO createOrder(CreateOrderDTO requestDTO);
    OrderResponseDTO updateOrder(Long id, UpdateOrderDTO requestDTO);
    OrderResponseDTO changeStatusOrder(Long orderId, OrderStatus newStatus);

    void markOrderAsPaid(Long orderId);
    void markOrderAsPaymentFailed(Long orderId);

    Mono<String> requestPaymentForOrder(PaymentRequestDTO paymentRequestDTO);
}
