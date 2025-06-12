package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "returnRequest")
@Getter
@Setter
public class ReturnRequest {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    private String reason;

    private LocalDate requestDate;
    private String status;
    private String sellerResponse;

//    public ReturnRequest(Long id,
//                         User customer,
//                         Product product,
//                         Order order,
//                         String reason,
//                         LocalDate requestDate,
//                         String status,
//                         String sellerResponse){
//        this.id = id;
//        this.customer = customer;
//        this.product = product;
//        this.order = order;
//        this.reason = reason;
//        this.requestDate = requestDate;
//        this.status = status;
//        this.sellerResponse = sellerResponse;
//    }
}
