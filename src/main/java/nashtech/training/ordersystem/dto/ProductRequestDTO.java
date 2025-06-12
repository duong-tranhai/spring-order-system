package nashtech.training.ordersystem.dto;

import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.entity.Discount;
import nashtech.training.ordersystem.entity.Supplier;

import java.time.LocalDateTime;
import java.util.List;

public record ProductRequestDTO(String name,
                                String description,
                                int stock,
                                double price,
                                Category category,
                                Supplier supplier,
                                LocalDateTime createdAt,
                                List<Discount> discounts){}

