package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
