package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDateTime createdAt;


    public Category(){}

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    public Category(Long id, String name, String description, LocalDateTime createdAt){
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }
}