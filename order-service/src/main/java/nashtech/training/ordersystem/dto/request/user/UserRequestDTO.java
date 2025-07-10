package nashtech.training.ordersystem.dto.request.user;

public record UserRequestDTO(
        String username,
        String email,
        String firstName,
        String lastName
) {}
