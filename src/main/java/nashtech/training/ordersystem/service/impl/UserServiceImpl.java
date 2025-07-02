package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.dto.request.user.UserRequestDTO;
import nashtech.training.ordersystem.dto.request.user.UserSearchFilter;
import nashtech.training.ordersystem.dto.response.user.UserResponseDTO;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.mapper.UserMapper;
import nashtech.training.ordersystem.repository.RoleRepository;
import nashtech.training.ordersystem.repository.UserRepository;
import nashtech.training.ordersystem.repository.specification.UserSpecification;
import nashtech.training.ordersystem.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final static String DEFAULT_PASSWORD = "123";

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Role CUSTOMER not found. Cannot create new user!"));

        User user = User.builder()
                .username(requestDTO.username())
                .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                .email(requestDTO.email())
                .firstName(requestDTO.firstName())
                .lastName(requestDTO.lastName())
                .roles(Set.of(customerRole))
                .build();

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true) // Use read-only transactions for query methods for better performance
    public List<UserResponseDTO> findAllWithFilters(UserSearchFilter filter) {
        // 1. Build the dynamic WHERE clause using our Specification builder
        Specification<User> spec = UserSpecification.build(filter);

        // 2. Build the dynamic ORDER BY clause
        // Default sort by 'createdAt' if no column is specified
        String sortColumn = (filter.column() != null && !filter.column().isBlank()) ? filter.column() : "createdAt";
        // Note: Sorting by a derived field like "fullName" is complex and would require a custom Sort.
        // This implementation assumes sorting by direct entity fields.
        if (sortColumn.equalsIgnoreCase("fullname") || sortColumn.equalsIgnoreCase("name")) {
            sortColumn = "firstName"; // Default sort for fullName
        }

        Sort.Direction direction = filter.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortColumn);

        // 3. Execute the query using the repository
        List<User> users = userRepository.findAll(spec, sort);

        // 4. Map the results to DTOs and return
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponseDTO assignRoles(Long id, Set<String> roles) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        Set<RoleName> requestedRoleNames = roles.stream()
                .map(roleStr -> {
                    try {
                        // We convert to uppercase to be more flexible with the input string
                        return RoleName.valueOf(roleStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Role '" + roleStr + "' is not a valid role.");
                    }
                })
                .collect(Collectors.toSet());

        Set<Role> newRoles = roleRepository.findByNameIn(requestedRoleNames);

        user.setRoles(newRoles);
        return userMapper.toDto(userRepository.save(user));
    }
}
