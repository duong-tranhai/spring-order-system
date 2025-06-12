package nashtech.training.ordersystem.controller;

import jakarta.validation.Valid;
import nashtech.training.ordersystem.dto.UserDTO;
import nashtech.training.ordersystem.entity.Role;
import nashtech.training.ordersystem.entity.User;
import nashtech.training.ordersystem.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class RoleController{
    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> ListOfUser(){
        return ResponseEntity.ok(roleService.getAllUser());
    }
    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> RoleAssgining(@PathVariable String username,
                                                 @Valid @RequestBody Role role){
        return ResponseEntity.ok(roleService.assignRole(username, role));
    }
}