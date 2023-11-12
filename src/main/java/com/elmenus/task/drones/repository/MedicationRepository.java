package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Medication} entities.
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {

    /**
     * Retrieves a list of loaded medications for a drone with the specified serial number.
     *
     * @param serialNumber The serial number of the drone.
     * @return A list of medications loaded on the drone with the specified serial number.
     */
    @Query("SELECT m FROM Medication m " +
            "JOIN m.droneMedications dm " +
            "WHERE dm.drone.serialNumber = :serialNumber")
    List<Medication> findLoadedMedicationsForDrone(@Param("serialNumber") String serialNumber);
}
