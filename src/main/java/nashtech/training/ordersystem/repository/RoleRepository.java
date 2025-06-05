package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
