package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
