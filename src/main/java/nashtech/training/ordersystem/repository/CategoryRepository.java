package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
