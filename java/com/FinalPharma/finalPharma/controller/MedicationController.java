package com.FinalPharma.finalPharma.controller;

import com.FinalPharma.finalPharma.model.Categories;
import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.repository.CategoriesRepo;
import com.FinalPharma.finalPharma.service.MedicationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;
    @Autowired
    private CategoriesRepo categoriesRepository;

    private static final Logger logger = LoggerFactory.getLogger(MedicationController.class);

    @PostMapping("/addMedication")
    public ResponseEntity<Medication> addMedication(@RequestBody @Valid Medication medication) {
        try {
            // Fetch the category from the repository
            Categories category = categoriesRepository.findById(medication.getCategory().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // Set the category for the medication
            medication.setCategory(category);

            // Save the medication
            Medication savedMedication = medicationService.addMedication(medication);

            return ResponseEntity.ok(savedMedication);
        } catch (Exception ex) {
            logger.error("Error occurred while adding medication: {}", ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/delete/{medicationId}")
    public void deleteMedication(@PathVariable Integer medicationId) {
        try {
            medicationService.deleteMedication(medicationId);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting medication with id {}: {}", medicationId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting medication with id " + medicationId + ": " + ex.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<Medication> getAllMedications() {
        try {
            return medicationService.getAllMedications();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all medications: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching all medications: " + ex.getMessage());
        }
    }

    @GetMapping("/{medicationId}")
    public Medication getMedicationById(@PathVariable Integer medicationId) {
        try {
            return medicationService.findMedicationById(medicationId);
        } catch (Exception ex) {
            logger.error("Error occurred while finding medication with id {}: {}", medicationId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding medication with id " + medicationId + ": " + ex.getMessage());
        }
    }

    

    @GetMapping("/name/{medName}")
    public Medication getMedicationByName(@PathVariable String medName) {
        try {
            return medicationService.findMedicationByName(medName);
        } catch (Exception ex) {
            logger.error("Error occurred while finding medication with name '{}': {}", medName, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding medication with name '" + medName + "': " + ex.getMessage());
        }
    }

    @PutMapping("/update/{medicationId}")
    public Medication updateMedication(@PathVariable Integer medicationId, @RequestBody Medication updatedMedication) {
        try {
            return medicationService.updateMedication(medicationId, updatedMedication);
        } catch (Exception ex) {
            logger.error("Error occurred while updating medication with id {}: {}", medicationId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while updating medication with id " + medicationId + ": " + ex.getMessage());
        }
    }

    @GetMapping("/products")
    public ModelAndView getProducts() {
    List<Medication> products = medicationService.getAllMedications();
    ModelAndView modelAndView = new ModelAndView("products");
    modelAndView.addObject("products", products);
    return modelAndView;
}

    
    
}
