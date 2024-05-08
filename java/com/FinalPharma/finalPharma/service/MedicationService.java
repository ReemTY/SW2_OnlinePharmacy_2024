package com.FinalPharma.finalPharma.service;

import com.FinalPharma.finalPharma.model.Medication;
import com.FinalPharma.finalPharma.repository.MedicationRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {
    private final MedicationRepo medicationRepository;
    private static final Logger logger = LoggerFactory.getLogger(MedicationService.class);

    @Autowired
    public MedicationService(MedicationRepo medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Transactional
    public Medication addMedication(Medication medication){
        try {
            return medicationRepository.save(medication);
        } catch (Exception ex) {
            logger.error("Error occurred while adding medication: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while adding medication: " + ex.getMessage());
        }
    }

    public void deleteMedication(Integer medicationId) {
        try {
            medicationRepository.deleteById(medicationId);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting medication with id {}: {}", medicationId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while deleting medication with id " + medicationId + ": " + ex.getMessage());
        }
    }

    public List<Medication> getAllMedications() {
        try {
            return medicationRepository.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all medications: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while fetching all medications: " + ex.getMessage());
        }
    }

    public Medication findMedicationById(Integer medicationId){
        try {
            return medicationRepository.findByMedicationId(medicationId);
        } catch (Exception ex) {
            logger.error("Error occurred while finding medication with id {}: {}", medicationId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding medication with id " + medicationId + ": " + ex.getMessage());
        }
    }

    public Medication findMedicationByName(String medName){
        try {
            return medicationRepository.findByMedName(medName);
        } catch (Exception ex) {
            logger.error("Error occurred while finding medication with name '{}': {}", medName, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while finding medication with name '" + medName + "': " + ex.getMessage());
        }
    }

    public Medication updateMedication(Integer medicationId, Medication updatedMedication) {
        try {
            Medication existingMedication = medicationRepository.findById(medicationId)
                    .orElseThrow(() -> new RuntimeException("Medication not found with id: " + medicationId));

            existingMedication.setMedName(updatedMedication.getMedName());
            existingMedication.setDescription(updatedMedication.getDescription());
            existingMedication.setPrice(updatedMedication.getPrice());
            existingMedication.setQuantity(updatedMedication.getQuantity());
            existingMedication.setCategory(updatedMedication.getCategory());

            return medicationRepository.save(existingMedication);
        } catch (Exception ex) {
            logger.error("Error occurred while updating medication with id {}: {}", medicationId, ex.getMessage(), ex);
            throw new RuntimeException("Error occurred while updating medication with id " + medicationId + ": " + ex.getMessage());
        }
    }
}
