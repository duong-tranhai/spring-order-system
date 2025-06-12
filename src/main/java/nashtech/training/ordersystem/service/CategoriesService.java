package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.CategoriesRequestDTO;
import nashtech.training.ordersystem.dto.CategoriesResponseDTO;

public interface CategoriesService {
    CategoriesResponseDTO createCategories(CategoriesRequestDTO dto);
    CategoriesResponseDTO updateCategories(Long id, CategoriesRequestDTO dto);
    void deleteCategories(Long id);
}