package nashtech.training.ordersystem.mapper;

import nashtech.training.ordersystem.dto.response.user.UserResponseDTO;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roleNames")
    @Mapping(source = "active", target = "isActive")
    UserResponseDTO toDto(User user);

    /**
     * This default method teaches MapStruct how to convert a single Role object
     * into a String. MapStruct will automatically use this method when it needs
     * to map the Set<Role> to a Set<String>.
     *
     * @param role The Role entity object.
     * @return The name of the role as a String.
     */
    default String mapRoleToString(Role role) {
        return role.getName().name();
    }
}
