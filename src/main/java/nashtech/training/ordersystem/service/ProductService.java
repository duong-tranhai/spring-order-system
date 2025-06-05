package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository repo) {
        this.productRepository = repo;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product p = new Product();
        p.setName(dto.name());
        p.setStock(dto.stock());
        p.setPrice(dto.price());
        p.setDescription(dto.description());
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
}
