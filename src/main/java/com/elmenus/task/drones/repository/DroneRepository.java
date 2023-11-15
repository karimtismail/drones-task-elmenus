package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.Drone;
import com.elmenus.task.drones.shared.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Drone} entities.
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {

    /**
     * Retrieves a list of drones by their state.
     *
     * @param state The state of the drones to retrieve.
     * @return A list of drones with the specified state.
     */
    List<Drone> findByState(DroneState state);

    /**
     * Retrieves a drone by its serial number.
     *
     * @param serialNumber The serial number of the drone to retrieve.
     * @return The drone with the specified serial number, or null if not found.
     */
    Drone findBySerialNumber(String serialNumber);

    /**
     * Retrieves a list of drones with battery capacity less than the specified value and not in the specified state.
     *
     * @param batteryCapacity The battery capacity.
     * @param droneState      The state of drones to exclude.
     * @return A list of drones with battery capacity less than the specified value and not in the specified state.
     */
    List<Drone> findByBatteryCapacityLessThanAndStateNot(Integer batteryCapacity, DroneState droneState);
}
