package nashtech.training.ordersystem.controller;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.Supplier;
import nashtech.training.ordersystem.service.ProductService;
import nashtech.training.ordersystem.service.SupplierService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier/products")
@RequiredArgsConstructor
public class SupplierController {

    private final ProductService productService;
    private final SupplierService supplierService; // Fetch current supplier

    @PostMapping
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO request, Authentication auth) {
        Supplier supplier = supplierService.findByName(auth.getName());
        ProductResponseDTO product = productService.createProduct(request,supplier);
        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
                                                 @RequestBody ProductRequestDTO request,
                                                 Authentication auth) {
        Supplier supplier = supplierService.findByName(auth.getName());
        ProductResponseDTO product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, Authentication auth) {
        Supplier supplier = supplierService.findByName(auth.getName());
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<List<Product>> getMyProducts(Authentication auth) {
        Supplier supplier = supplierService.findByName(auth.getName());
        return ResponseEntity.ok(productService.getProductsBySupplier(supplier));
    }

    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam int stock, Authentication auth) {
        Supplier supplier = supplierService.findByName(auth.getName());
        productService.updateStock(id, stock, supplier);
        return ResponseEntity.ok("Stock updated");
    }
}
