package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.*;
import nashtech.training.ordersystem.entity.*;
import nashtech.training.ordersystem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


//public interface OrderService {
//}

@Service
public class OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(String customerUsername, OrderRequestDTO orderRequestDTO){
        User customer = userRepository.findByUsername(customerUsername).orElseThrow(()->new RuntimeException("Customer not found"));
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(String.valueOf(OrderStatus.PENDING));

        List<OrderItem> orderItems = orderRequestDTO.items().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(()-> new RuntimeException("Product not found" + itemDTO.productId()));
            if (product.getStock() < itemDTO.quantity()){
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - itemDTO.quantity());
            productRepository.save(product);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.quantity());
            orderItem.setPrice(product.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return toResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getOrdersForUser(String username, Role role){
        List<Order> orders;
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if (role.getName() == RoleName.ROLE_CUSTOMER){
            orders = orderRepository.findByCustomer(user);
        } else if (role.getName() == RoleName.ROLE_SELLER) {
            orders = orderRepository.findBySeller(user);
        } else{
            orders = orderRepository.findAll();
        }

        return orders.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        order.setStatus(String.valueOf(newStatus));
        Order updated = orderRepository.save(order);
        return toResponseDTO(updated);
    }

    private OrderResponseDTO toResponseDTO(Order order){
        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(oi -> new OrderItemResponseDTO(
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getPrice()
                )).collect(Collectors.toList());
        String sellerUsername = order.getSeller() != null ? order.getSeller().getUsername(): null;

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getUsername(),
                sellerUsername,
                order.getStatus(),
                items,
                order.getCreatedAt()
        );
    }

}