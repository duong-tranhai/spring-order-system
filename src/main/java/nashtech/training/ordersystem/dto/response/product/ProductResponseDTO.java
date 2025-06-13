package nashtech.training.ordersystem.dto.response.product;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String name, String description, Integer stock, BigDecimal price) {
}
