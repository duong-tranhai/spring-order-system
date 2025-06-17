package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.lang.ScopedValue;
import java.util.Optional;

public interface OrderRepository extends  JpaRepository<Order, Long>,JpaSpecificationExecutor<Order> {
}
