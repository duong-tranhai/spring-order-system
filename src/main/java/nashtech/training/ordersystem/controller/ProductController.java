package nashtech.training.ordersystem.controller;

import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService svc) {
        this.productService = svc;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> listProduct(@PathVariable Long id,
                                               @Valid ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
                                                            @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/discount")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseDTO> applyDiscount(@PathVariable Long id,
                                                            @RequestParam("discountPercentage") double discountPercentage) {
        ProductResponseDTO updatedProduct = productService.applyDiscount(id, discountPercentage);
        return ResponseEntity.ok(updatedProduct);
    }
}
