package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.product.ProductRequestDTO;
import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getById(Long id);

    ProductResponseDTO addToCategories(Long id, Set<String> categoryNames);

    void deleteProduct(Long id);

    void softDelete(String supplierUsername, Long id);
}
