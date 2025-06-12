package nashtech.training.ordersystem.dto;

import java.util.Set;

public record UserResponseDTO(Long id, String username, Set<String> roles) {
}
