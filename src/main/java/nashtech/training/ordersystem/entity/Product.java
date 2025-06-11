package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category", // Name of the join table
            joinColumns = @JoinColumn(name = "product_id"), // Foreign key from product
            inverseJoinColumns = @JoinColumn(name = "category_id") // Foreign key from category
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    private Supplier supplier;

}
