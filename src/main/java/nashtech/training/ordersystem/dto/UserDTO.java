package nashtech.training.ordersystem.dto;

import nashtech.training.ordersystem.entity.Role;

import java.util.Set;

public record UserDTO(String username, String password, Set<Role> roles) {
}

