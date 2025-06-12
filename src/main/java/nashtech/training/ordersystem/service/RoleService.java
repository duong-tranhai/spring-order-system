package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.UserDTO;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.User;

import java.util.List;

public interface RoleService {
    List<User> getAllUser();
    UserDTO assignRole(String username, Role role);
}