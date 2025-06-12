package nashtech.training.ordersystem.service.impl;

import jdk.jfr.Category;
import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository repository){
        this.productRepository = repository;
    }
    @Override
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
    @Override
    public List<ProductResponseDTO> getAllProducts(){
        return productRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        Product updated = productRepository.save(product);
        return toResponseDTO(updated);
    }
    @Override
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

//    public Product getProductById(Long id){
//        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
//    }
    @Override
    public ProductResponseDTO getProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        return toResponseDTO(product);
    }
    private ProductResponseDTO toResponseDTO(Product product){
        return new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getStock(), product.getPrice(), (nashtech.training.ordersystem.entity.Category) product.getCategory(), (nashtech.training.ordersystem.entity.Supplier) product.getSupplier());
    }
}
