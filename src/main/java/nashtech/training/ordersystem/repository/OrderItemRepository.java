package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{}