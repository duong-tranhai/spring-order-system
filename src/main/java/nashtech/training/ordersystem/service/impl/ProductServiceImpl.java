package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.Supplier;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.repository.SupplierRepository;
import nashtech.training.ordersystem.service.ProductService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public ProductServiceImpl(ProductRepository repo, SupplierRepository supplierRepository) {
        this.productRepository = repo;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto, Supplier supplier) {
        Product p = new Product();
        p.setName(dto.name());
        p.setStock(dto.stock());
        p.setPrice(dto.price());
        p.setDescription(dto.description());
        p.setSupplier(supplier);
        Product saved = productRepository.save(p);
        return toResponseDTO(saved);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto, Supplier supplier) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!p.getSupplier().getId().equals(supplier.getId())) {
            throw new RuntimeException("You are not authorized to update this product.");
        }

        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        Product updated = productRepository.save(p);
        return toResponseDTO(updated);
    }

    @Override
    public void deleteProduct(Long id, Supplier supplier) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!p.getSupplier().getId().equals(supplier.getId())) {
            throw new RuntimeException("You are not authorized to update this product.");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getProductsBySupplier(Supplier supplier) {
        return productRepository.findBySupplierId(supplier.getId());
    }

    @Override
    public void updateStock(Long productId, int stock, Supplier supplier) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSupplier().getId().equals(supplier.getId())) {
            throw new AccessDeniedException("You are not allowed to update this product");
        }

        product.setStock(stock);
        productRepository.save(product);
    }

    @Override
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

    private ProductResponseDTO toResponseDTO(Product p) {
        return new ProductResponseDTO(p.getId(), p.getName(), p.getDescription(), p.getStock(), p.getPrice());
    }
}
