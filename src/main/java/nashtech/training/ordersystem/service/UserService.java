package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User registerUser(User user);
}
