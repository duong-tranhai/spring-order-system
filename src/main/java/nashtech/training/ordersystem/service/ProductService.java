package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.Supplier;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.repository.SupplierRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    public ProductService(ProductRepository repo, SupplierRepository supplierRepository) {
        this.productRepository = repo;
        this.supplierRepository = supplierRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto,Supplier supplier) {
        Product p = new Product();
        p.setName(dto.name());
        p.setStock(dto.stock());
        p.setPrice(dto.price());
        p.setDescription(dto.description());
        p.setSupplier(supplier);
        Product saved = productRepository.save(p);
        return toResponseDTO(saved);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        Product updated = productRepository.save(p);
        return toResponseDTO(updated);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private ProductResponseDTO toResponseDTO(Product p) {
        return new ProductResponseDTO(p.getId(), p.getName(), p.getDescription(), p.getStock(), p.getPrice());
    }

    public List<Product> getProductsBySupplier(Supplier supplier) {
        return productRepository.findBySupplierId(supplier.getId());
    }

    public void updateStock(Long productId, int stock, Supplier supplier) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSupplier().getId().equals(supplier.getId())) {
            throw new AccessDeniedException("You are not allowed to update this product");
        }

        product.setStock(stock);
        productRepository.save(product);
    }

    public ProductResponseDTO applyDiscount(Long productId, double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100%");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double originalPrice = product.getPrice();
        double discountedPrice = originalPrice * (1 - discountPercentage / 100.0);

        product.setPrice(discountedPrice);
        Product updated = productRepository.save(product);

        return toResponseDTO(updated);
    }
}
