package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {

    @Query("SELECT m FROM Medication m " +
            "JOIN DroneMedication dm " +
            "WHERE dm.drone.serialNumber = :serialNumber " +
            "AND dm.drone.state = 'LOADED'")
    List<Medication> findLoadedMedicationsForDrone(@Param("serialNumber") String serialNumber);
}

