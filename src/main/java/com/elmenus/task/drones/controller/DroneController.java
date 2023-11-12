package com.elmenus.task.drones.controller;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.AuditLog;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.exception.BatteryLowException;
import com.elmenus.task.drones.exception.DroneStateException;
import com.elmenus.task.drones.exception.WeightExceededException;
import com.elmenus.task.drones.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Validated
@RestController
@RequestMapping("/api/drones")
public class DroneController {

    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @ExceptionHandler({BatteryLowException.class, WeightExceededException.class, DroneStateException.class})
    public ResponseEntity<String> handleCustomExceptions(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<DroneDTO> registerDrone(@Valid @RequestBody DroneDTO droneDTO) {
        Optional<DroneDTO> registeredDrone = droneService.registerDrone(droneDTO);
        return registeredDrone
                .map(drone -> new ResponseEntity<>(drone, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<DroneDTO> loadDroneWithMedications(
            @PathVariable String serialNumber,
            @Valid @RequestBody Set<MedicationDTO> medications) {
        Optional<DroneDTO> loadedDrone = droneService.loadDroneWithMedications(serialNumber, medications);
        return loadedDrone
                .map(drone -> new ResponseEntity<>(drone, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneDTO>> getAvailableDronesForLoading() {
        List<DroneDTO> availableDrones = droneService.getAvailableDronesForLoading();
        return new ResponseEntity<>(availableDrones, HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable String serialNumber) {
        int batteryLevel = droneService.checkDroneBatteryLevel(serialNumber);
        return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<AuditLog>> getAuditLogEvents() {
        List<AuditLog> auditLogEvents = droneService.getAuditLogEvents();
        return new ResponseEntity<>(auditLogEvents, HttpStatus.OK);
    }

    @PostMapping("/{serialNumber}/change-state/{newState}")
    public ResponseEntity<DroneDTO> changeDroneState(
            @PathVariable String serialNumber,
            @PathVariable DroneState newState) {
        Optional<DroneDTO> changedDrone = droneService.changeDroneState(serialNumber, newState);
        return changedDrone
                .map(drone -> new ResponseEntity<>(drone, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
