package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.Categories;
//import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.service.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @PostMapping("/addCategory")
    public Categories addCategory(@RequestBody Categories category) {
        try {
            return categoriesService.addCategory(category);
        } catch (Exception ex) {
            logger.error("Error occurred while adding category: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while adding category: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId) {
        try {
            categoriesService.deleteCategory(categoryId);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting category with id " + categoryId + ": " + ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = categoriesService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }



    @GetMapping("/{categoryId}")
    public Categories getCategoryById(@PathVariable Integer categoryId) {
        try {
            return categoriesService.findCategoryById(categoryId);
        } catch (Exception ex) {
            logger.error("Error occurred while finding category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding category with id " + categoryId + ": " + ex.getMessage());
        }
    }

    @GetMapping("/name/{categoryName}")
    public Categories getCategoryByName(@PathVariable String categoryName) {
        try {
            return categoriesService.findCategoryByName(categoryName);
        } catch (Exception ex) {
            logger.error("Error occurred while finding category with name '{}': {}", categoryName, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding category with name '" + categoryName + "': " + ex.getMessage());
        }
    }

    @PutMapping("/update/{categoryId}")
    public Categories updateCategory(@PathVariable Integer categoryId, @RequestBody Categories updatedCategory) {
        try {
            return categoriesService.updateCategory(categoryId, updatedCategory);
        } catch (Exception ex) {
            logger.error("Error occurred while updating category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while updating category with id " + categoryId + ": " + ex.getMessage());
        }
    }


    @GetMapping("/{categoryId}/medications")
    public List<Medication> getMedicationsByCategoryId(@PathVariable Integer categoryId) {
        try {
            return categoriesService.getMedicationsByCategoryId(categoryId);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching medications for category with id {}: {}", categoryId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching medications for category with id " + categoryId + ": " + ex.getMessage());
        }
    }

}
