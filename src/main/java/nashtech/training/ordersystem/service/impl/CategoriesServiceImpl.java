package nashtech.training.ordersystem.service.impl;

import nashtech.training.ordersystem.dto.CategoriesRequestDTO;
import nashtech.training.ordersystem.dto.CategoriesResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.repository.CategoriesRepository;
import nashtech.training.ordersystem.service.CategoriesService;


public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepoitory;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepoitory) {
        this.categoriesRepoitory = categoriesRepoitory;
    }
    @Override
    public CategoriesResponseDTO createCategories(CategoriesRequestDTO dto){
        Category category = new Category(dto.id(), dto.name(), dto.description(), dto.createdAt());
        Category saved = categoriesRepoitory.save(category);
        return toResponseDTO(saved);
    }
    @Override
    public CategoriesResponseDTO updateCategories(Long id, CategoriesRequestDTO dto){
        Category category = categoriesRepoitory.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setCreatedAt(dto.createdAt());
        Category updated = categoriesRepoitory.save(category);
        return toResponseDTO(updated);
    }
    @Override
    public void deleteCategories(Long id){
        categoriesRepoitory.deleteById(id);
    }

    private CategoriesResponseDTO toResponseDTO(Category category){
        return new CategoriesResponseDTO(category.getId(), category.getName());
    }
}