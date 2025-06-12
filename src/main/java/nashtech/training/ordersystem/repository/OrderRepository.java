package nashtech.training.ordersystem.repository;


import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User customer);

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double sumTotalSales();
}
