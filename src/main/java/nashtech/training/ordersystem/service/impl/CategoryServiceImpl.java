package nashtech.training.ordersystem.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.training.ordersystem.dto.request.category.CategoryRequestDTO;
import nashtech.training.ordersystem.dto.response.category.CategoryResponseDTO;
import nashtech.training.ordersystem.entity.Category;
import nashtech.training.ordersystem.mapper.CategoryMapper;
import nashtech.training.ordersystem.repository.CategoryRepository;
import nashtech.training.ordersystem.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final static Integer BATCH_SIZE = 50;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        return null;
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        return null;
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<CategoryResponseDTO> createBatchCategories(List<CategoryRequestDTO> dtoList) {
        List<Category> allSavedCategories = new ArrayList<>();

        List<Category> categoriesToSave = dtoList.stream()
                .map(dto -> {
                    Category category = new Category();
                    category.setName(dto.name());
                    category.setDescription(dto.description());
                    category.setPrefix(dto.prefix());
                    // Set other properties as needed from your DTO
                    return category;
                })
                .toList();

        for (int i = 0; i < categoriesToSave.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, categoriesToSave.size());
            List<Category> batch = categoriesToSave.subList(i, endIndex);

            // Save the current batch
            List<Category> savedBatch = categoryRepository.saveAll(batch);
            allSavedCategories.addAll(savedBatch);
        }

        return allSavedCategories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
