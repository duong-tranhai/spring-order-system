package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Entity
@Table(name = "products")
@Setter
@Getter

public class Product{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Discount> discounts;

    public Product(){}

    public Product(String name,
                   String description,
                   int stock,
                   double price,
                   Category category,
                   Supplier supplier,
                   LocalDateTime createdAt,
                   List<Discount> discounts){
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.category = category;
        this.supplier = supplier;
        this.createdAt = createdAt;
        this.discounts = discounts;
    }

    public long getId(){ return id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name;}

    public String getDescription(){ return description;}
    public void setDescription(String description){ this.description = description;}

    public int getStock(){return stock;}
    public void setStock(int stock){this.stock = stock;}

    public double getPrice(){return price;}
    public void setPrice(double price){this.price = price;}



}