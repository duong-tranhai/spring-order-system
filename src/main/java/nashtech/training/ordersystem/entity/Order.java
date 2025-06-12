package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table( name= "orders")

public class Order{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private User seller;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private String paymentStatus;

    public Order() {}

    @PrePersist
    public void prePersist(){
        orderDate = LocalDateTime.now();
    }

    public Long getId(){return id;}
    public User getCustomer(){return customer;}
    public void setCustomer(User customer){this.customer = customer;}

    public User getSeller(){return seller;}
    public void setSeller(User seller){this.seller = seller;}

    public String getStatus(){return status;}
    public void setStatus(String status) {this.status =status;}

    public List<OrderItem> getItems(){return items;}
    public void setItems(List<OrderItem> items){this.items = items;}

    public LocalDateTime getCreatedAt(){return orderDate;}

    public LocalTime getOrderDate() {

    }
}