package org.example.pfa.IService;

import org.example.pfa.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category CreateCategory (Category category);

    List<Category> getAllCategory();
    Category getCategoryByID(Long id);
    Category updateCategory(Long id,Category category);
    void deleteCategory(Long id);







}
