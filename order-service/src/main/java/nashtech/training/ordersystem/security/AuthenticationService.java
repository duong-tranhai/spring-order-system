package nashtech.training.ordersystem.security;

import nashtech.training.ordersystem.dto.request.authentication.AuthenticationRequest;
import nashtech.training.ordersystem.dto.request.authentication.RegisterRequest;
import nashtech.training.ordersystem.dto.response.authentication.AuthenticationResponse;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.RoleName;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.RoleRepository;
import nashtech.training.ordersystem.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 RoleRepository roleRepository, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        Role defaultRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Not found CUSTOMER Role!"));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);  // Add default role to user
        user.setRoles(roles);
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthenticationResponse(jwtToken);
    }

    /**
     * Authenticates a user using Spring Security's AuthenticationManager.
     * This approach automatically throws specific exceptions on failure,
     * which are then caught by the GlobalExceptionHandler.
     *
     * @param request The authentication request containing username and password.
     * @return An AuthenticationResponse containing the JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // The authenticate method will throw BadCredentialsException or DisabledException
        // if the credentials are bad or the user is disabled. These are caught by the
        // GlobalExceptionHandler, which returns a 401 or 403 status.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // If authentication is successful, we proceed to generate the token.
        // We fetch UserDetails again to ensure we have the most up-to-date user state.
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

        String jwtToken = jwtService.generateToken(userDetails.getUsername());
        return new AuthenticationResponse(jwtToken);
    }
}

