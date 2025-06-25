package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerUsernameAndIsDeletedFalse(String username);
    Optional<Order> findByIdAndIsDeletedFalse(Long id);
}
