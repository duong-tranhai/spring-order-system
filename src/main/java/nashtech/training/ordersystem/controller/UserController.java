package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.request.user.UserRequestDTO;
import nashtech.training.ordersystem.dto.request.user.UserSearchFilter;
import nashtech.training.ordersystem.dto.response.user.UserResponseDTO;
import nashtech.training.ordersystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.createUser(requestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, requestDTO));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(
            @RequestParam(required = false) String column,
            @RequestParam(required = false) String value,
            @RequestParam(defaultValue = "false") boolean isAsc) {
        UserSearchFilter filter = new UserSearchFilter(column, value, isAsc);
        return ResponseEntity.ok(userService.findAllWithFilters(filter));
    }

}
