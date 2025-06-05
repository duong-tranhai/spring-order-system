package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.OrderRequestDTO;
import nashtech.training.ordersystem.dto.OrderResponseDTO;
import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.service.OrderService;
import nashtech.training.ordersystem.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService service){
        this.orderService = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDTO> createOrder(Authentication auth,
                                                        @RequestBody OrderRequestDTO orderRequestDTO){
        String username = auth.getName();
        OrderResponseDTO createdOrder = orderService.createOrder(username, orderRequestDTO);
        return ResponseEntity.status(201).body(createdOrder);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SELLER', 'ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Authentication auth){
        String username = auth.getName();
        Role role = Role.valueOf("ROLE_" + auth.getAuthorities().stream().findFirst().get().getAuthority().replace("ROLE_", ""));
        List<OrderResponseDTO> orders = orderService.getOrdersForUser(username, role);
        return ResponseEntity.ok(orders);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long orderId,
                                                              @RequestParam("status") String status){
        OrderResponseDTO updated = orderService.updateOrderStatus(orderId, Enum.valueOf(nashtech.training.ordersystem.entity.OrderStatus.class, status));
        return ResponseEntity.ok(updated);
    }
}
