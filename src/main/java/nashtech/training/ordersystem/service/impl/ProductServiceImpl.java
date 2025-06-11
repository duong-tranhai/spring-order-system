package nashtech.training.ordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.request.product.ProductRequestDTO;
import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.mapper.ProductMapper;
import nashtech.training.ordersystem.repository.ProductRepository;
import nashtech.training.ordersystem.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setName(requestDTO.name());
        product.setDescription(requestDTO.description());
        product.setStock(requestDTO.stock());
        product.setPrice(requestDTO.price());
        product.setCategories(Collections.emptySet());

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

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
}
