package nashtech.training.ordersystem.dto.request.product;

import java.math.BigDecimal;

public record ProductRequestDTO(String name, String description, Integer stock, BigDecimal price) {
}
