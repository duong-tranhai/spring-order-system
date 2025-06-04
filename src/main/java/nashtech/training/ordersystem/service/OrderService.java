package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.*;
import nashtech.training.ordersystem.entity.*;
import nashtech.training.ordersystem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public OrderResponseDTO createOrder(String customerUsername, OrderRequestDTO orderRequestDTO) {
        User customer = userRepository.findByUsername(customerUsername)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        Set<Product> orderedProducts = new HashSet<>();

        for (OrderItemResponseDTO itemDTO : orderRequestDTO.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.productId()));

            if (product.getStock() < itemDTO.quantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Reduce stock
            product.setStock(product.getStock() - itemDTO.quantity());
            productRepository.save(product); // optional if using JPA transactional context

            // Add product to order
            orderedProducts.add(product);
        }

        order.setProducts(orderedProducts);
        Order savedOrder = orderRepository.save(order);

        return toResponseDTO(savedOrder);
    }
    public List<OrderResponseDTO> getOrdersForUser(String username, RoleName roleName) {
        List<Order> orders;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Objects.requireNonNull(roleName) == RoleName.ROLE_CUSTOMER) {
            orders = orderRepository.findByCustomer(user);
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        return toResponseDTO(order);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getProducts().stream()
                .map(product -> new OrderItemResponseDTO(
                        product.getId(),
                        product.getName(),
                        1,
                        product.getPrice()
                )).collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getUsername(),
                order.getStatus().name(),
                items,
                order.getOrderDate()
        );
    }
    public void requestReturn(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getCustomer().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to request a return for this order");
        }

        if (!order.isReturnEligible()) {
            throw new RuntimeException("Order is not eligible for return");
        }

        order.setReturnRequested(true);
        orderRepository.save(order);
    }
    public void approveReturn(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.isReturnRequested()) {
            throw new RuntimeException("No return request found for this order");
        }

        order.setReturnApproved(true);
        order.setStatus(OrderStatus.CANCELLED); // optional new status
        orderRepository.save(order);
    }
}
