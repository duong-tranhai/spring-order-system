package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The order this payment is associated with.
     * We use FetchType.LAZY to avoid loading the entire order object
     * unless we explicitly need it, which is a major performance best practice.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * The amount for this specific transaction.
     * Using BigDecimal is essential for handling currency to avoid floating-point errors.
     */
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * The method used for this payment. Stored as a readable string in the database
     * (e.g., "CASH_ON_DELIVERY") for clarity and safety.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    /**
     * The status of this specific payment transaction.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderPaymentStatus status;

    /**
     * The unique transaction ID provided by the external payment gateway (e.g., MoMo, Stripe).
     * This can be null for offline methods like Cash on Delivery.
     */
    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    private OrderPaymentStatus paymentStatus;
}
