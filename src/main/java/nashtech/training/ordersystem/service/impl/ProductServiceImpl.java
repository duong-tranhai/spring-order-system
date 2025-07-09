package nashtech.training.ordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.request.product.ProductRequestDTO;
import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.mapper.ProductMapper;
import nashtech.training.ordersystem.repository.CategoryRepository;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = Product.builder()
                .name(requestDTO.name())
                .description(requestDTO.description())
                .stock(requestDTO.stock())
                .price(requestDTO.price())
                .categories(Collections.emptySet())
                .build();

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.isDeleted()) throw new RuntimeException("Product is deleted!");

        product.setName(requestDTO.name());
        product.setDescription(requestDTO.description());
        product.setStock(requestDTO.stock());
        product.setPrice(requestDTO.price());
//        product.setCategories();
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found Product with id:"+id));
        return productMapper.toDto(product);
    }

    @Override
    public ProductResponseDTO addToCategories(Long id, Set<String> categoryNames) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Set<String> upperCaseCategoryNames = categoryNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        Set<Category> categories = categoryRepository.findByNamesIgnoreCaseAndNotDeleted(upperCaseCategoryNames);

        existingProduct.setCategories(categories);
        return productMapper.toDto(productRepository.save(existingProduct));
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setDeleted(true);
        existingProduct.setDeletedAt(LocalDateTime.now());

        productRepository.save(existingProduct);
    }
}
