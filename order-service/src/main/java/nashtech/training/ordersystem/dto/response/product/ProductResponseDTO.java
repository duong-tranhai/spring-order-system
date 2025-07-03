package nashtech.training.ordersystem.dto.response.product;

import nashtech.training.ordersystem.dto.response.category.CategoryResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        Integer stock,
        BigDecimal price,
        List<CategoryResponseDTO> categories
) {}
