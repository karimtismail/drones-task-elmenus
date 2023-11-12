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

/**
 * Controller class for managing drone-related operations.
 */
@Validated
@RestController
@RequestMapping("/api/drones")
public class DroneController {

    private final DroneService droneService;

    /**
     * Constructor for DroneController.
     *
     * @param droneService The service responsible for handling drone-related operations.
     */
    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * Exception handler for BatteryLowException, WeightExceededException, and DroneStateException.
     *
     * @param e The exception to be handled.
     * @return ResponseEntity with an error message and HTTP status.
     */
    @ExceptionHandler({BatteryLowException.class, WeightExceededException.class, DroneStateException.class})
    public ResponseEntity<String> handleCustomExceptions(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Endpoint for registering a new drone.
     *
     * @param droneDTO The DTO representing the drone to be registered.
     * @return ResponseEntity with the registered drone DTO and HTTP status.
     */
    @PostMapping("/register")
    public ResponseEntity<DroneDTO> registerDrone(@Valid @RequestBody DroneDTO droneDTO) {
        Optional<DroneDTO> registeredDrone = droneService.registerDrone(droneDTO);
        return registeredDrone
                .map(drone -> new ResponseEntity<>(drone, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Endpoint for loading a drone with medications.
     *
     * @param serialNumber The serial number of the drone.
     * @param medications  Set of MedicationDTO representing medications to be loaded.
     * @return ResponseEntity with the loaded drone DTO and HTTP status.
     */
    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<DroneDTO> loadDroneWithMedications(
            @PathVariable String serialNumber,
            @Valid @RequestBody Set<MedicationDTO> medications) {
        Optional<DroneDTO> loadedDrone = droneService.loadDroneWithMedications(serialNumber, medications);
        return loadedDrone
                .map(drone -> new ResponseEntity<>(drone, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Endpoint for retrieving available drones for loading.
     *
     * @return ResponseEntity with the list of available drone DTOs and HTTP status.
     */
    @GetMapping("/available")
    public ResponseEntity<List<DroneDTO>> getAvailableDronesForLoading() {
        List<DroneDTO> availableDrones = droneService.getAvailableDronesForLoading();
        return new ResponseEntity<>(availableDrones, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving the battery level of a drone.
     *
     * @param serialNumber The serial number of the drone.
     * @return ResponseEntity with the battery level and HTTP status.
     */
    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable String serialNumber) {
        int batteryLevel = droneService.checkDroneBatteryLevel(serialNumber);
        return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving audit log events.
     *
     * @return ResponseEntity with the list of audit log events and HTTP status.
     */
    @GetMapping("/events")
    public ResponseEntity<List<AuditLog>> getAuditLogEvents() {
        List<AuditLog> auditLogEvents = droneService.getAuditLogEvents();
        return new ResponseEntity<>(auditLogEvents, HttpStatus.OK);
    }

    /**
     * Endpoint for changing the state of a drone.
     *
     * @param serialNumber The serial number of the drone.
     * @param newState     The new state to set for the drone.
     * @return ResponseEntity with the changed drone DTO and HTTP status.
     */
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