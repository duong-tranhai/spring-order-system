package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.ReturnRequestItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestItemsRepository extends JpaRepository<ReturnRequestItems, Long> {
    List<ReturnRequestItems> findByReturnRequestId(Long returnRequestId);

}
