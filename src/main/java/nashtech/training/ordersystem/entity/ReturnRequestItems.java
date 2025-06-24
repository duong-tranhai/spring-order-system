package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "return_requests_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequestItems extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "return_request_id", nullable = false)
    private ReturnRequests returnRequest;

    @ManyToOne
    @JoinColumn(name = "order_item_id",nullable = false)
    private OrderItem orderItem;

    private Integer quantity;

}
