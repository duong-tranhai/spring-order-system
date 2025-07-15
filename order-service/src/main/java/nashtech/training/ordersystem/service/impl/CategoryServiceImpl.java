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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final static Integer BATCH_SIZE = 50;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        if (categoryRepository.existsByNameAndIsDeletedFalse(dto.name())) {
            throw new RuntimeException("An active category with the name '" + dto.name() + "' already exists.");
        }
        if (categoryRepository.existsByPrefixAndIsDeletedFalse(dto.prefix())) {
            throw new RuntimeException("An active category with the prefix '" + dto.prefix() + "' already exists.");
        }
        Optional<Category> existingDeletedCategory = categoryRepository.findByNameAndIsDeletedTrue(dto.name());

        Category category;
        if (existingDeletedCategory.isPresent()) {
            category = existingDeletedCategory.get();

            // Update fields from the new request
            category.setDescription(dto.description());
            category.setPrefix(dto.prefix());

            // Reactivate the entity by resetting the soft-delete fields
            category.setDeleted(false);
            category.setDeletedAt(null);
        } else {
            category = Category.builder()
                    .name(dto.name())
                    .description(dto.description())
                    .prefix(dto.prefix())
                    .build();
        }
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category existingCategory = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(dto.name());
        existingCategory.setDescription(dto.description());
        existingCategory.setPrefix(dto.prefix());

        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
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
                .map(dto -> Category.builder()
                        .name(dto.name())
                        .description(dto.description())
                        .prefix(dto.prefix())
                        .build())
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

    @Override
    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setDeleted(true);
        existingCategory.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(existingCategory);
    }
}
