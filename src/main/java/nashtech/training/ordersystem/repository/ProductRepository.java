package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
