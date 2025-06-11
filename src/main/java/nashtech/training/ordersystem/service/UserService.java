package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.user.UserRequestDTO;
import nashtech.training.ordersystem.dto.request.user.UserSearchFilter;
import nashtech.training.ordersystem.dto.response.user.UserResponseDTO;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO);
    List<UserResponseDTO> findAll();
    List<UserResponseDTO> findAllWithFilters(UserSearchFilter filter);
    UserResponseDTO findById(Long id);
    UserResponseDTO findByUsername(String username);
    void deactivateUser(Long id);
    UserResponseDTO assignRoles(Long id, Set<String> roles);
}
