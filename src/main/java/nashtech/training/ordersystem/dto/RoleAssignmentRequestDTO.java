package nashtech.training.ordersystem.dto;

import nashtech.training.ordersystem.entity.RoleName;
import java.util.Set;

public record RoleAssignmentRequestDTO(
        Long userId,
        Set<RoleName> roles
) {}
