package nashtech.training.ordersystem.repository.specification;

import jakarta.persistence.criteria.Expression;
import nashtech.training.ordersystem.dto.request.ReturnRequest.ReturnRequestSearchFilter;
import nashtech.training.ordersystem.entity.ReturnRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class ReturnRequestSpecification {
    public static Specification<ReturnRequest> build(ReturnRequestSearchFilter filter){
        if (filter.column() == null || filter.column().isBlank() || filter.value() == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return switch (filter.column().toLowerCase()){
            case "product" -> (root, query, cb) ->
                    cb.like(cb.lower(root.get("product")), "%" + filter.value().toLowerCase() + "%");
            case "username" -> (root, query, cb) ->
                    cb.like(cb.lower(root.get("username")), "%" + filter.value().toLowerCase() + "%");
            case "fullname", "name" -> (root, query, cb) -> {
                // This is a multi-line lambda block, so we must use 'return'
                Expression<String> fullName = cb.concat(root.get("firstName"), " ");
                fullName = cb.concat(fullName, root.get("lastName"));
                return cb.like(cb.lower(fullName), "%" + filter.value().toLowerCase() + "%");
            };
            default ->
                    (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        };
    }
}