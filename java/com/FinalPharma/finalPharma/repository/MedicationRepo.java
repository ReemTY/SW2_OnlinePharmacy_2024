package com.FinalPharma.finalPharma.repository;

import com.FinalPharma.finalPharma.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationRepo extends JpaRepository<Medication, Integer>{
    Medication findByMedicationId(Integer medicationId);
    Medication findByMedName(String medName);

}
