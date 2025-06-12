package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.dto.*;
import nashtech.training.ordersystem.entity.*;
import nashtech.training.ordersystem.repository.*;
import nashtech.training.ordersystem.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
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

            product.setStock(product.getStock() - itemDTO.quantity());
            productRepository.save(product);
            orderedProducts.add(product);
        }

        order.setProducts(orderedProducts);
        Order savedOrder = orderRepository.save(order);

        return toResponseDTO(savedOrder);
    }

    @Override
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

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);
        return toResponseDTO(order);
    }

    @Override
    public void requestReturn(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getCustomer().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to request a return for this order");
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new RuntimeException("Only completed orders are eligible for return");
        }
        order.setStatus(OrderStatus.REQUEST_RETURNED);
        orderRepository.save(order);
    }

    @Override
    public void approveReturn(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.REQUEST_RETURNED) {
            throw new RuntimeException("No return request found for this order");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getCustomer().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to request a return for this order");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders are eligible for return");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
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
}

