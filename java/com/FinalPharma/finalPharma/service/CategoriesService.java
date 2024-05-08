package com.FinalPharma.finalPharma.service;

import com.FinalPharma.finalPharma.model.Categories;
//import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.repository.CategoriesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    private final CategoriesRepo categoriesRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);

    public CategoriesService(CategoriesRepo categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Categories addCategory(Categories category) {
        try {
            return categoriesRepository.save(category);
        } catch (Exception ex) {
            logger.error("Error occurred while adding category: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while adding category: " + ex.getMessage());
        }
    }

    public void deleteCategory(Integer categoryId) {
        try {
            categoriesRepository.deleteById(categoryId);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting category with id " + categoryId + ": " + ex.getMessage());
        }
    }

    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public Categories findCategoryById(Integer categoryId) {
        try {
            return categoriesRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        } catch (Exception ex) {
            logger.error("Error occurred while finding category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding category with id " + categoryId + ": " + ex.getMessage());
        }
    }

    public Categories findCategoryByName(String categoryName) {
        try {
            return categoriesRepository.findByCategoryName(categoryName);
        } catch (Exception ex) {
            logger.error("Error occurred while finding category with name '{}': {}", categoryName, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding category with name '" + categoryName + "': " + ex.getMessage());
        }
    }

    public Categories updateCategory(Integer categoryId, Categories updatedCategory) {
        try {
            Categories existingCategory = categoriesRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

            existingCategory.setCategoryName(updatedCategory.getCategoryName());

            return categoriesRepository.save(existingCategory);
        } catch (Exception ex) {
            logger.error("Error occurred while updating category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while updating category with id " + categoryId + ": " + ex.getMessage());
        }
    }

    public List<Medication> getMedicationsByCategoryId(Integer categoryId) {
        try {
            Categories category = findCategoryById(categoryId);
            return category.getMedications();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching medications for category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching medications for category with id " + categoryId + ": " + ex.getMessage());
        }
    }
}


