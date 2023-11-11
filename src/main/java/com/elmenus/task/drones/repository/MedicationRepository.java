package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Integer> {

}