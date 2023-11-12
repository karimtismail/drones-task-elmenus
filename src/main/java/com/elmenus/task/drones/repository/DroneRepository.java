package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.Drone;
import com.elmenus.task.drones.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {
    List<Drone> findByState(DroneState state);

    Drone findBySerialNumber(String serialNumber);

    List<Drone> findByBatteryCapacityLessThanAndStateNot(Integer batteryCapacity, DroneState droneState);
}