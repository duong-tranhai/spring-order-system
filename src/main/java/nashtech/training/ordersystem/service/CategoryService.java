package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.request.category.CategoryRequestDTO;
import nashtech.training.ordersystem.dto.response.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);
    CategoryResponseDTO findById(Long id);
    List<CategoryResponseDTO> findAll();
    List<CategoryResponseDTO> createBatchCategories(List<CategoryRequestDTO> dtoList);
}
