package nashtech.training.ordersystem.dto;

import java.util.Set;

public record UserDTO(String username, String password, Set<String> roles) {
}

