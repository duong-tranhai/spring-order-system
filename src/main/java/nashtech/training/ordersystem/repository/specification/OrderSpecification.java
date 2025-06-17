package nashtech.training.ordersystem.repository.specification;

import nashtech.training.ordersystem.dto.request.order.OrderSearchFilter;
import nashtech.training.ordersystem.entity.Order;
import nashtech.training.ordersystem.entity.OrderPaymentStatus;
import nashtech.training.ordersystem.entity.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    public static Specification<Order> build(OrderSearchFilter filter) {
        if (filter.column() == null ||
                filter.column().isBlank() ||
                filter.value() == null ||
                filter.value().isBlank()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return switch (filter.column().toLowerCase()) {

            case "status" -> (root, query, cb) ->
                    cb.equal(root.get("status"),
                            OrderStatus.valueOf(filter.value().toUpperCase()) );

            case "paymentstatus" -> (root, query, cb) ->
                    cb.equal(root.get("paymentStatus"),
                            OrderPaymentStatus.valueOf(filter.value().toUpperCase()) );

            case "customer" -> (root, query, cb) ->
                    cb.like(cb.lower(root.get("customer").get("username")),
                            "%" + filter.value().toLowerCase() + "%");

            default -> (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        };
    }
}
