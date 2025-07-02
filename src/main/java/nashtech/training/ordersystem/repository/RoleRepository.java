package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

    /**
     * Finds all Role entities whose names are in the provided collection of RoleName enums.
     * This will translate to an efficient 'WHERE name IN (...) ' SQL query.
     * @param names A collection of RoleName enums.
     * @return A Set of matching Role entities.
     */
    Set<Role> findByNameIn(Collection<RoleName> names);
}
