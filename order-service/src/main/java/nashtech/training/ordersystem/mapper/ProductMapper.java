package nashtech.training.ordersystem.mapper;

import nashtech.training.ordersystem.dto.response.product.ProductResponseDTO;
import nashtech.training.ordersystem.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categories", target = "categories")
    ProductResponseDTO toDto(Product product);
}
