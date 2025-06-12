package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder encoder){
        this.userRepository = repository;
        this.passwordEncoder = encoder;
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}