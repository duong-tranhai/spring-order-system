package nashtech.training.ordersystem.mapper;

import nashtech.training.ordersystem.dto.response.category.CategoryResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toDto(Category category);
}
