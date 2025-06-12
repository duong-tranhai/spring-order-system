package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.dto.UserDTO;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.UserRepository;
import nashtech.training.ordersystem.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    public RoleServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getAllUser(){
        return new ArrayList<>(userRepository.findAll());
    }
    @Override
    public UserDTO assignRole(String username, Role role){
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        user.setRole((Set<Role>) role);
        User newRole = userRepository.save(user);
        return toUserDTO(newRole);
    }

    private UserDTO toUserDTO(User user){
        return new UserDTO(user.getUsername(), user.getPassword(), user.getRole());
    }
}