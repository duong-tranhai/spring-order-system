package nashtech.training.ordersystem.controller;


import nashtech.training.ordersystem.dto.request.product.ProductRequestDTO;
import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;
import nashtech.training.ordersystem.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER', 'ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER', 'SELLER', 'ADMIN')")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPPLIER', 'SELLER', 'ADMIN')")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}/categories")
    @PreAuthorize("hasAnyRole('SUPPLIER', 'ADMIN')")
    public ResponseEntity<ProductResponseDTO> assignCategoriesToProduct(
            @PathVariable Long id,
            @RequestBody Set<String> categoryNames) {
        ProductResponseDTO updatedProductDto = productService.addToCategories(id, categoryNames);
        return ResponseEntity.ok(updatedProductDto);
    }
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<?> softDeleteProduct(@PathVariable Long productId,
                                               Authentication auth) {
        productService.softDelete(auth.getName(), productId);
        return ResponseEntity.ok("Product soft-deleted successfully.");
    }

}
