package nashtech.training.ordersystem.dto.response.user;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        Boolean isActive,
        List<String> roleNames
) {}
