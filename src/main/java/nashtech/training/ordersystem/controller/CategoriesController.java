package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.CategoriesRequestDTO;
import nashtech.training.ordersystem.dto.CategoriesResponseDTO;
import jakarta.validation.Valid;
import nashtech.training.ordersystem.service.CategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService){
        this.categoriesService = categoriesService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriesResponseDTO> createCategories(@Valid @RequestBody CategoriesRequestDTO dto){
        CategoriesResponseDTO categories = categoriesService.createCategories(dto);
        return ResponseEntity.status(201).body(categories);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriesResponseDTO> updateCategories(@PathVariable Long id,
                                                                  @Valid @RequestBody CategoriesRequestDTO dto){
        return ResponseEntity.ok(categoriesService.updateCategories(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategories(@PathVariable Long id){
        categoriesService.deleteCategories(id);
        return ResponseEntity.noContent().build();
    }

}