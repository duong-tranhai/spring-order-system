package nashtech.training.ordersystem.service;

import jdk.jfr.Category;
import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProductService{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository repository){
        this.productRepository = repository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto){
        Product product = new Product(dto.name(),
                dto.description(),
                dto.stock(),
                dto.price(),
                (Category) dto.category(),
                (Supplier) dto.supplier(),
                dto.createdAt(),
                dto.discounts());
        Product saved =productRepository.save(product);
        return toResponseDTO(saved);
    }

    public List<ProductResponseDTO> getAllProducts(){
        return productRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        Product updated = productRepository.save(product);
        return toResponseDTO(updated);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

//    public Product getProductById(Long id){
//        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
//    }

    public ProductResponseDTO getProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        return toResponseDTO(product);
    }
    private ProductResponseDTO toResponseDTO(Product product){
        return new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getStock(), product.getPrice(), (nashtech.training.ordersystem.entity.Category) product.getCategory(), (nashtech.training.ordersystem.entity.Supplier) product.getSupplier());
    }
}
