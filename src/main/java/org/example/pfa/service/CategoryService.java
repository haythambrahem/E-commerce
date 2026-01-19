package org.example.pfa.service;

import lombok.RequiredArgsConstructor;
import org.example.pfa.IService.ICategoryService;
import org.example.pfa.dto.CategoryDTO;
import org.example.pfa.entity.Category;
import org.example.pfa.exception.ResourceNotFoundException;
import org.example.pfa.mapper.CategoryMapper;
import org.example.pfa.repository.CategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepo categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public Category CreateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category createCategoryFromDTO(CategoryDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAllCategoriesPaged(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryByID(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        
        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());
        
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public Category updateCategoryFromDTO(Long id, CategoryDTO dto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        
        existingCategory.setName(dto.getName());
        existingCategory.setDescription(dto.getDescription());
        
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category", id);
        }
        categoryRepository.deleteById(id);
    }
}
