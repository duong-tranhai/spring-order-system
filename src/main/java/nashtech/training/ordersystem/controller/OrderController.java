package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.OrderRequestDTO;
import nashtech.training.ordersystem.dto.OrderResponseDTO;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import nashtech.training.ordersystem.repository.RoleRepository;
import nashtech.training.ordersystem.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService svc) {
        this.orderService = svc;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDTO> createOrder(Authentication auth,
                                                        @RequestBody OrderRequestDTO orderRequestDTO) {
        String username = auth.getName();
        OrderResponseDTO createdOrder = orderService.createOrder(username, orderRequestDTO);
        return ResponseEntity.status(201).body(createdOrder);
    }

    @GetMapping
    @PreAuthorize("hasRole('SELLER','CUSTOMER')")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Authentication auth) {
        String username = auth.getName();

        String authority = auth.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles assigned"))
                .getAuthority();

        RoleName roleName = RoleName.valueOf(authority.replace("ROLE_", ""));

        List<OrderResponseDTO> orders = orderService.getOrdersForUser(username, roleName);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long orderId,
                                                              @RequestParam("status") String status) {
        OrderResponseDTO updated = orderService.updateOrderStatus(orderId, Enum.valueOf(nashtech.training.ordersystem.entity.OrderStatus.class, status));
        return ResponseEntity.ok(updated);
    }
    @PostMapping("/{orderId}/return")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> requestReturn(@PathVariable Long orderId, Authentication auth) {
        String username = auth.getName();
        orderService.requestReturn(orderId, username);
        return ResponseEntity.ok("Return request submitted successfully");
    }

    @PutMapping("/{orderId}/return/approve")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> approveReturn(@PathVariable Long orderId) {
        orderService.approveReturn(orderId);
        return ResponseEntity.ok("Return request approved");
    }
}