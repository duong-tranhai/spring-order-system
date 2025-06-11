package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.request.order.CreateOrderDTO;
import nashtech.training.ordersystem.dto.request.order.UpdateOrderDTO;
import nashtech.training.ordersystem.dto.request.payment.PaymentRequestDTO;
import nashtech.training.ordersystem.dto.response.order.OrderResponseDTO;
import nashtech.training.ordersystem.entity.OrderStatus;
import nashtech.training.ordersystem.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderDTO request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SELLER', 'ADMIN')")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SELLER', 'ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderDTO request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SELLER')")
    public ResponseEntity<OrderResponseDTO> changeStatusOrder(
            @PathVariable Long id,
            @RequestParam("newStatus") OrderStatus newStatus) {
        return ResponseEntity.ok(orderService.changeStatusOrder(id, newStatus));
    }

    // In your Controller
    @PostMapping("/request-payment-order")
    public Mono<Map<String, String>> initiatePayment(@RequestBody PaymentRequestDTO request) {
        return orderService.requestPaymentForOrder(request)
                .map(clientSecret -> Map.of("clientSecret", clientSecret)); // Map the result to a JSON object
    }
}
