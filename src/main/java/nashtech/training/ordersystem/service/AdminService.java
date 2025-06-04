package nashtech.training.ordersystem.service;

import jakarta.transaction.Transactional;
import nashtech.training.ordersystem.dto.CategoryRequestDTO;
import nashtech.training.ordersystem.dto.ReportResponseDTO;
import nashtech.training.ordersystem.dto.UserDTO;
import nashtech.training.ordersystem.dto.UserResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository,
                        CategoryRepository categoryRepository,ProductRepository productRepository,
                        OrderRepository orderRepository,PasswordEncoder encoder,EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = encoder;
        this.emailService = emailService;
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

    public ReportResponseDTO generateAdminReports() {
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        double totalSales = orderRepository.sumTotalSales();
        long totalProducts = productRepository.count();

        return new ReportResponseDTO(totalUsers, totalOrders, totalSales, totalProducts);
    }
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public UserResponseDTO createUser(UserDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        Set<RoleName> roleNames = request.roles().stream()
                .map(String::toUpperCase)
                .map(RoleName::valueOf)
                .collect(Collectors.toSet());

        Set<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.size() != request.roles().size()) {
            throw new RuntimeException("One or more roles are invalid");
        }

        user.setRoles(roles);
        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.username());

        if (request.password() != null && !request.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        Set<RoleName> roleNames = request.roles().stream()
                .map(String::toUpperCase)
                .map(RoleName::valueOf)
                .collect(Collectors.toSet());

        Set<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.size() != request.roles().size()) {
            throw new RuntimeException("One or more roles are invalid");
        }

        user.setRoles(roles);
        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void sendEmailNotification(String to, String subject, String body) {
        emailService.sendEmail(to, subject, body);
    }

}
