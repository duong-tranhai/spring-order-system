package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.ReturnRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestsRepository extends JpaRepository<ReturnRequests, Long> {
    List<ReturnRequests> findByUserId(Long userId);
}
