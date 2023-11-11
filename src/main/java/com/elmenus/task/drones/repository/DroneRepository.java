package com.elmenus.task.drones.repository;

import com.elmenus.task.drones.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Integer> {

}