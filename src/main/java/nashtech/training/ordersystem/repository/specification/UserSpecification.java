package nashtech.training.ordersystem.repository.specification;

import jakarta.persistence.criteria.Expression;
import nashtech.training.ordersystem.dto.request.user.UserSearchFilter;
import nashtech.training.ordersystem.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    /**
     * Creates a Specification for the User entity based on the search filter.
     * This version uses the modern, non-deprecated API for creating specifications.
     * @param filter The filter containing column, value, and sort order.
     * @return A Specification<User> that can be used in repository queries.
     */
    public static Specification<User> build(UserSearchFilter filter) {
        if (filter.column() == null || filter.column().isBlank() || filter.value() == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return switch (filter.column().toLowerCase()) {
            case "username" -> (root, query, cb) ->
                    cb.like(cb.lower(root.get("username")), "%" + filter.value().toLowerCase() + "%");

            case "email" -> (root, query, cb) ->
                    cb.like(cb.lower(root.get("email")), "%" + filter.value().toLowerCase() + "%");

            case "fullname", "name" -> (root, query, cb) -> {
                // This is a multi-line lambda block, so we must use 'return'
                Expression<String> fullName = cb.concat(root.get("firstName"), " ");
                fullName = cb.concat(fullName, root.get("lastName"));
                return cb.like(cb.lower(fullName), "%" + filter.value().toLowerCase() + "%");
            };

            case "isactive" -> (root, query, cb) -> {
                boolean isActive = Boolean.parseBoolean(filter.value());
                return cb.equal(root.get("isActive"), isActive); // Using 'return' here too
            };

            default ->
                    (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        };
    }
}
