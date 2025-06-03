package nashtech.training.ordersystem.security;

import nashtech.training.ordersystem.dto.AuthenticationRequest;
import nashtech.training.ordersystem.dto.AuthenticationResponse;
import nashtech.training.ordersystem.dto.RegisterRequest;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of("ROLE_CUSTOMER")); // default role
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthenticationResponse(jwtToken);
    }
}

