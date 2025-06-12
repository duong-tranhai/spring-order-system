package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "discount")
@Getter
@Setter
public class Discount{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double percentage;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private User seller;

    @ManyToOne
    private Product product;

    public Discount(Long id, String name, double percentage, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}