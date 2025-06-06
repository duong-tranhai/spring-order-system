package nashtech.training.ordersystem.service;

import jakarta.transaction.Transactional;
import nashtech.training.ordersystem.dto.CategoryRequestDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.CategoryRepository;
import nashtech.training.ordersystem.repository.RoleRepository;
import nashtech.training.ordersystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void assignRolesToUser(Long userId, Set<RoleName> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> roles = roleRepository.findByNameIn(roleNames);

        if (roles.size() != roleNames.size()) {
            throw new RuntimeException("One or more roles not found");
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    public Category createCategory(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return categoryRepository.save(category);
    }

    // Update category and return Category entity
    @Transactional
    public Category updateCategory(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.name());
        category.setDescription(dto.description());
        return categoryRepository.save(category);
    }

    // Delete category by id
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

}
