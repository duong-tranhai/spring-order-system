package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String contactInfo;

    @Column
    @ManyToOne
    private Set<Product> products;

    @Column
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    public Supplier(Long id, String name, String contactInfo, Set<Product> products, LocalDateTime createdAt){
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.products = products;
        this.createdAt = createdAt;
    }
}