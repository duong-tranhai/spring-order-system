package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.ProductRequestDTO;
import nashtech.training.ordersystem.dto.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import nashtech.training.ordersystem.entity.Supplier;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto, Supplier supplier);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto, Supplier supplier);
    void deleteProduct(Long id, Supplier supplier);
    Product getProductById(Long id);
    List<Product> getProductsBySupplier(Supplier supplier);
    void updateStock(Long productId, int stock, Supplier supplier);
    ProductResponseDTO applyDiscount(Long productId, double discountPercentage);
}
