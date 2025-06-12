package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findBySellerUsername(String username);
}
