package org.example.pfa.mapper;

import org.example.pfa.dto.CategoryDTO;
import org.example.pfa.entity.Category;
import org.example.pfa.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .productIds(category.getProducts() != null 
                        ? category.getProducts().stream().map(Product::getId).toList() 
                        : Collections.emptyList())
                .build();
    }

    public Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public List<CategoryDTO> toDTOList(List<Category> categories) {
        if (categories == null) return Collections.emptyList();
        return categories.stream().map(this::toDTO).toList();
    }
}
