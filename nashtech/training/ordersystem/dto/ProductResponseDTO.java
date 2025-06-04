package nashtech.training.ordersystem.dto;

public record ProductResponseDTO(Long id, String name, String description, int stock, double price){}