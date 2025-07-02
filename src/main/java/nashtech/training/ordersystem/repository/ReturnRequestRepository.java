package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    List<ReturnRequest> findByCustomerUsername(String username);
    List<ReturnRequest> findByProduct_SupplierUsername(String username);
}
