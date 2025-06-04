package nashtech.training.ordersystem.repository;


import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findBySeller(User seller);
}
