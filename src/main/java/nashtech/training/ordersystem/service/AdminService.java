package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.CategoryRequestDTO;
import nashtech.training.ordersystem.dto.ReportResponseDTO;
import nashtech.training.ordersystem.dto.UserDTO;
import nashtech.training.ordersystem.dto.UserResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.entity.RoleName;

import java.util.List;
import java.util.Set;

public interface AdminService {

    void assignRolesToUser(Long userId, Set<RoleName> roleNames);

    Category createCategory(CategoryRequestDTO dto);

    Category updateCategory(Long id, CategoryRequestDTO dto);

    void deleteCategory(Long id);

    ReportResponseDTO generateAdminReports();

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO createUser(UserDTO request);

    UserResponseDTO updateUser(Long id, UserDTO request);

    void deleteUser(Long id);

    void sendEmailNotification(String to, String subject, String body);
}
