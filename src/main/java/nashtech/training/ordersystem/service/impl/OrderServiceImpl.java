package nashtech.training.ordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.request.order.CreateOrderDTO;
import nashtech.training.ordersystem.dto.request.order.OrderItemRequestDTO;
import nashtech.training.ordersystem.dto.request.order.OrderSearchFilter;
import nashtech.training.ordersystem.dto.request.order.UpdateOrderDTO;
import nashtech.training.ordersystem.dto.response.order.OrderResponseDTO;
import nashtech.training.ordersystem.entity.*;
import nashtech.training.ordersystem.mapper.OrderMapper;
import nashtech.training.ordersystem.repository.OrderRepository;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.repository.UserRepository;
import nashtech.training.ordersystem.repository.specification.OrderSpecification;
import nashtech.training.ordersystem.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDTO getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found order!"));
        return orderMapper.toOrderDto(order);
    }

    @Override
    public List<OrderResponseDTO> getAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(orderMapper::toOrderDto).toList();
    }

    @Override
    @Transactional(readOnly = true) // Use read-only transactions for query methods for better performance
    public List<OrderResponseDTO> getAllWithFilters(OrderSearchFilter filter) {
        // 1. Build the dynamic WHERE clause using our Specification builder
        Specification<Order> spec = OrderSpecification.build(filter);

        // 2. Determine the Sort direction and column
        String sortColumn = (filter.column() != null && !filter.column().isBlank()) ? filter.column() : "orderDate";
        Sort.Direction direction = (filter.isAsc()) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        // 3. Query the repository
        List<Order> orders = orderRepository.findAll(spec, sort);

        // 4. Map the results to DTOs
        return orders.stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO requestDTO) {
        User customer = userRepository.findByUsername(requestDTO.username())
                .orElseThrow(() -> new RuntimeException("Not found user!"));

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .customer(customer)
                .status(OrderStatus.PENDING)
                .paymentStatus(OrderPaymentStatus.valueOf(requestDTO.paymentStatus()))
                .shippingAddress(requestDTO.shippingAddress())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequestDTO itemDto: requestDTO.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.productId()));

            if (product.getStock() < itemDto.quantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Reduce stock
            product.setStock(product.getStock() - itemDto.quantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            // Calculate total amount
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity())));
        }
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        order.setCreatedBy(customer.getUsername());

        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    @Transactional // Ensure this method is transactional for database operations
    public OrderResponseDTO updateOrder(Long id, UpdateOrderDTO requestDTO) {
        Order existedOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (existedOrder.isDeleted()) {
            throw new RuntimeException("The order is deleted!");
        }

        if (!existedOrder.getStatus().equals(OrderStatus.PENDING)) {
            throw new RuntimeException("Order can only be updated in pending status!");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Map the existing OrderItem list with product_id be object key,
        // due to the unique constraint (order_id, product_id) in OrderItem
        Map<Long, OrderItem> existingOrderItemsMap = existedOrder.getOrderItems().stream()
                .collect(Collectors.toMap(item -> item.getProduct().getId(), Function.identity()));

        Set<Long> productIdsInRequest = new HashSet<>();

        for (OrderItemRequestDTO updateOrderItemDTO : requestDTO.items()) {
            productIdsInRequest.add(updateOrderItemDTO.productId());

            Product product = productRepository.findById(updateOrderItemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + updateOrderItemDTO.productId()));

            if (product.getStock() < updateOrderItemDTO.quantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = existingOrderItemsMap.get(updateOrderItemDTO.productId());
            if (orderItem != null) {
                // Item already exists, update its quantity and stock
                int quantityDifference = updateOrderItemDTO.quantity() - orderItem.getQuantity();

                product.setStock(product.getStock() - quantityDifference);
                productRepository.save(product); // Save product to update stock

                orderItem.setQuantity(updateOrderItemDTO.quantity());
                orderItem.setPrice(product.getPrice()); // Update price in case it changed
                // No need to add to existedOrder.getOrderItems() as it's already there
            } else {
                // New item, create and add to the collection
                product.setStock(product.getStock() - updateOrderItemDTO.quantity());
                productRepository.save(product); // Save product to update stock

                orderItem = new OrderItem();
                orderItem.setOrder(existedOrder); // Crucial for establishing the relationship
                orderItem.setProduct(product);
                orderItem.setQuantity(updateOrderItemDTO.quantity());
                orderItem.setPrice(product.getPrice());

                existedOrder.getOrderItems().add(orderItem); // Add to the *managed* collection
            }

            // Calculate total amount
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(updateOrderItemDTO.quantity())));
        }

        // --- Step 2: Remove items that are no longer in the request ---
        // Use an Iterator to safely remove elements while iterating
        Iterator<OrderItem> iterator = existedOrder.getOrderItems().iterator();
        while (iterator.hasNext()) {
            OrderItem existingOrderItem = iterator.next();
            if (!productIdsInRequest.contains(existingOrderItem.getProduct().getId())) {
                // This item is no longer in the request, so remove it.
                // Also, return the stock to the product for the removed item.
                Product product = existingOrderItem.getProduct();
                product.setStock(product.getStock() + existingOrderItem.getQuantity());
                productRepository.save(product); // Save product to update stock
                iterator.remove(); // This removes from the managed collection, triggering orphanRemoval
            }
        }

        existedOrder.setTotalAmount(totalAmount);
        existedOrder.setPaymentStatus(OrderPaymentStatus.valueOf(requestDTO.paymentStatus()));
        existedOrder.setShippingAddress(requestDTO.shippingAddress());

        // No need to call setOrderItems(orderItems) here as we've been modifying
        // the existing collection directly.
        return orderMapper.toOrderDto(orderRepository.save(existedOrder));
    }

    @Override
    public OrderResponseDTO changeStatusOrder(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus currentStatus = order.getStatus();
        // No change needed if status is the same
        if (currentStatus == newStatus) {
            return orderMapper.toOrderDto(order);
        }

        // 2. State Transition Logic
        validateTransition(currentStatus, newStatus);

        order.setStatus(newStatus);
        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    /**
     * Private helper method to enforce valid state transitions.
     */
    private void validateTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // Terminal states cannot be changed
        if (currentStatus == OrderStatus.COMPLETED || currentStatus == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status from a terminal state: " + currentStatus);
        }

        boolean isValid = switch (currentStatus) {
            case PENDING -> newStatus == OrderStatus.PROCESSING || newStatus == OrderStatus.CANCELLED;
            case PROCESSING -> newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELLED;
            case SHIPPED -> newStatus == OrderStatus.DELIVERED;
            case DELIVERED -> newStatus == OrderStatus.COMPLETED || newStatus == OrderStatus.RETURN_REQUESTED;
            case RETURN_REQUESTED -> newStatus == OrderStatus.RETURNED || newStatus == OrderStatus.CANCELLED; // e.g., return request denied
            case RETURNED -> newStatus == OrderStatus.COMPLETED;
            default -> false; // Deny any other transitions by default
        };

        if (!isValid) {
            throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
    }
}
