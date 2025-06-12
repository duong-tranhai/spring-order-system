package nashtech.training.ordersystem.dto;

import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.entity.Supplier;

public record ProductResponseDTO(Long id,
                                 String name,
                                 String description,
                                 int stock,
                                 double price,
                                 Category category,
                                 Supplier supplier){}