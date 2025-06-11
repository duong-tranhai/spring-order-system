package nashtech.training.ordersystem.mapper;

import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toDto(Product product);
}
