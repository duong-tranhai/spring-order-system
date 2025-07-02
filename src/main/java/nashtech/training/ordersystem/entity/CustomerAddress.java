package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "customerAddress")
@Getter
@Setter
public class CustomerAddress {
    @Id @GeneratedValue
    private BigInteger id;

    private BigInteger user_id;

    @Column(columnDefinition = "TEXT")
    private String recipient_name;

    @Column(columnDefinition = "TEXT")
    private String phone_number;

    @Column(columnDefinition = "TEXT")
    private String street_address;

    @Column(columnDefinition = "TEXT")
    private String ward;

    @Column(columnDefinition = "TEXT")
    private String district;

    @Column(columnDefinition = "TEXT")
    private String city_province;

    private boolean is_default;

}