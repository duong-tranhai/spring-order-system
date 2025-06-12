package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    public OrderItem(){}

    public Long getId(){return id;}
    public Order getOrder(){return order;}
    public void setOrder(Order order){ this.order = order;}

    public Product getProduct(){return product;}
    public void setProduct(Product product){ this.product = product;}

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity = quantity;}

    public double getPrice(){return price;}
    public void setPrice(double price){this.price = price;}

}