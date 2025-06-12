package nashtech.training.ordersystem.dto;

import java.time.LocalDateTime;

public record CategoriesRequestDTO(Long id, String name, String description, LocalDateTime createdAt) {
}