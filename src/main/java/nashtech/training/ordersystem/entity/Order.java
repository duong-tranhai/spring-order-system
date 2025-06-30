package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)  // Store enum as a string in the database
    private OrderStatus orderStatus; //

    private BigDecimal totalAmount;
    private String shippingAddress;

    private String note;

    @Enumerated(EnumType.STRING)  // Store enum as a string in the database
    private OrderPaymentStatus orderPaymentStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer; //

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();
}
