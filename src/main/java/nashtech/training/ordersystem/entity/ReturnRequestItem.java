package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "returnRequestItem")
@Getter
@Setter

public class ReturnRequestItem {
    @Id @GeneratedValue
    private BigInteger id;

    private BigInteger return_request_id;

    private BigInteger order_item_id;
    @Column(unique = true)
    private int quantity;

}