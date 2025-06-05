package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Order() {}

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    public Long getId(){return id;}
    public User getCustomer(){return customer;}
    public void setCustomer(User customer){this.customer = customer;}

    public User getSeller(){return seller;}
    public void setSeller(User seller){this.seller = seller;}

    public OrderStatus getStatus(){return status;}
    public void setStatus(OrderStatus status) {this.status =status;}

    public List<OrderItem> getItems(){return items;}
    public void setItems(List<OrderItem> items){this.items = items;}

    public LocalDateTime getCreatedAt(){return createdAt;}
}