package nashtech.training.ordersystem.repository;

import nashtech.training.ordersystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndIsDeletedTrue(String name);

    Optional<Category> findByNameAndIsDeletedFalse(String name);

    Optional<Category> findByIdAndIsDeletedFalse(Long id);

    boolean existsByNameAndIsDeletedFalse(String name);
    boolean existsByPrefixAndIsDeletedFalse(String prefix);

    /**
     * Finds all active (not deleted) categories where the name matches any of the provided names, ignoring case.
     * This is the most efficient way to perform this lookup.
     *
     * @param names A collection of category names to search for. These should be prepared (e.g., converted to uppercase) in the service layer.
     * @return A Set of matching Category entities.
     */
    @Query("SELECT c FROM Category c WHERE UPPER(c.name) IN :names AND c.isDeleted = false")
    Set<Category> findByNamesIgnoreCaseAndNotDeleted(@Param("names") Collection<String> names);
}
