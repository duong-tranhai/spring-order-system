package nashtech.training.ordersystem.dto;

public record ProductRequestDTO(
        String name,
        String description,
        Integer stock,
        Double price
) {}
