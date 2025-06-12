package nashtech.training.ordersystem.service;

import nashtech.training.ordersystem.dto.CategoriesRequestDTO;
import nashtech.training.ordersystem.dto.CategoriesResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.repository.CategoriesRepository;

public class CategoriesService {
    private final CategoriesRepository categoriesRepoitory;

    public CategoriesService(CategoriesRepository categoriesRepoitory) {
        this.categoriesRepoitory = categoriesRepoitory;
    }

    public CategoriesResponseDTO createCategories(CategoriesRequestDTO dto){
        Category category = new Category(dto.id(), dto.name(), dto.description(), dto.createdAt());
        Category saved = categoriesRepoitory.save(category);
        return toResponseDTO(saved);
    }

    public CategoriesResponseDTO updateCategories(Long id, CategoriesRequestDTO dto){
        Category category = categoriesRepoitory.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setCreatedAt(dto.createdAt());
        Category updated = categoriesRepoitory.save(category);
        return toResponseDTO(updated);
    }

    public void deleteCategories(Long id){
        categoriesRepoitory.deleteById(id);
    }

    private CategoriesResponseDTO toResponseDTO(Category category){
        return new CategoriesResponseDTO(category.getId(), category.getName());
    }
}