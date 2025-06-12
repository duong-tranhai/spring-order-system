package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);
    void deleteProduct(Long id);
    ProductResponseDTO getProduct(Long id);
}