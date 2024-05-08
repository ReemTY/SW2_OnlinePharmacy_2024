package com.FinalPharma.finalPharma.repository;

import com.FinalPharma.finalPharma.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Categories, Integer>{
    Categories findByCategoryId(Integer categoryId);
    Categories findByCategoryName(String categoryName);
}    
    
