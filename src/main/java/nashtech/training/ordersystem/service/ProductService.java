package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.product.ProductRequestDTO;
import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getById(Long id);
}
