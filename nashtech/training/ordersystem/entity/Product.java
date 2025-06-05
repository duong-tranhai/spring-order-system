package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")

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

    public Product(){}

    public Product(String name, String description, int stock, double price){
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
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