package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.CategoryRequestDTO;
import nashtech.training.ordersystem.dto.RoleAssignmentRequestDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/assign-roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignRoles(@RequestBody RoleAssignmentRequestDTO request) {
        adminService.assignRolesToUser(request.userId(), request.roles());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO request) {
        Category category = adminService.createCategory(request);
        return ResponseEntity.status(201).body(category);
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody CategoryRequestDTO request) {
        Category updatedCategory = adminService.updateCategory(id, request);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
