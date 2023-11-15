package com.elmenus.task.drones.controller;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.AuditLog;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.exception.*;
import com.elmenus.task.drones.service.DroneService;
import com.elmenus.task.drones.utility.ApiResponse;
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
     * Exception handler for BatteryLowException, BatteryHighException, BatteryEqualException, WeightExceededException, DroneStateException, and DroneNotFoundException.
     *
     * @param e The exception to be handled.
     * @return ResponseEntity with an error message and HTTP status.
     */
    @ExceptionHandler({
            BatteryLowException.class,
            BatteryHighException.class,
            BatteryEqualException.class,
            WeightExceededException.class,
            DroneStateException.class,
            DroneNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> handleCustomExceptions(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    /**
     * Endpoint for registering a new drone.
     *
     * @param droneDTO The DTO representing the drone to be registered.
     * @return ResponseEntity with the registered drone DTO and HTTP status.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DroneDTO>> registerDrone(@Valid @RequestBody DroneDTO droneDTO) {
        Optional<DroneDTO> registeredDrone = droneService.registerDrone(droneDTO);
        return registeredDrone
                .map(drone -> ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(drone)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint for loading a drone with medications.
     *
     * @param serialNumber The serial number of the drone.
     * @param medications  Set of MedicationDTO representing medications to be loaded.
     * @return ResponseEntity with the loaded drone DTO and HTTP status.
     */
    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<ApiResponse<DroneDTO>> loadDroneWithMedications(
            @PathVariable String serialNumber,
            @Valid @RequestBody Set<MedicationDTO> medications) {
        Optional<DroneDTO> loadedDrone = droneService.loadDroneWithMedications(serialNumber, medications);
        return loadedDrone
                .map(drone -> ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(drone)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint for retrieving available drones for loading.
     *
     * @return ResponseEntity with the list of available drone DTOs and HTTP status.
     */
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<DroneDTO>>> getAvailableDronesForLoading() {
        List<DroneDTO> availableDrones = droneService.getAvailableDronesForLoading();
        return ResponseEntity.ok(ApiResponse.success(availableDrones));
    }

    /**
     * Endpoint for retrieving the battery level of a drone.
     *
     * @param serialNumber The serial number of the drone.
     * @return ResponseEntity with the battery level and HTTP status.
     */
    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<ApiResponse<Integer>> getDroneBatteryLevel(@PathVariable String serialNumber) {
        int batteryLevel = droneService.checkDroneBatteryLevel(serialNumber);
        return ResponseEntity.ok(ApiResponse.success(batteryLevel));
    }

    /**
     * Endpoint for retrieving audit log events.
     *
     * @return ResponseEntity with the list of audit log events and HTTP status.
     */
    @GetMapping("/events")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditLogEvents() {
        List<AuditLog> auditLogEvents = droneService.getAuditLogEvents();
        return ResponseEntity.ok(ApiResponse.success(auditLogEvents));
    }

    /**
     * Endpoint for changing the state of a drone.
     *
     * @param serialNumber The serial number of the drone.
     * @param newState     The new state to set for the drone.
     * @return ResponseEntity with the changed drone DTO and HTTP status.
     */
    @PostMapping("/{serialNumber}/change-state/{newState}")
    public ResponseEntity<ApiResponse<DroneDTO>> changeDroneState(
            @PathVariable String serialNumber,
            @PathVariable DroneState newState) {
        Optional<DroneDTO> changedDrone = droneService.changeDroneState(serialNumber, newState);
        return changedDrone
                .map(drone -> ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(drone)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint for changing the battery capacity of a drone.
     *
     * @param serialNumber       The serial number of the drone.
     * @param newBatteryCapacity The new battery capacity to set for the drone.
     * @return ResponseEntity with the changed drone DTO and HTTP status.
     */
    @PostMapping("/{serialNumber}/change-battery-capacity/{newBatteryCapacity}")
    public ResponseEntity<ApiResponse<DroneDTO>> changeBatteryCapacity(
            @PathVariable String serialNumber,
            @PathVariable int newBatteryCapacity
    ) {
        if (newBatteryCapacity < 0 || newBatteryCapacity > 100) {
            return ResponseEntity.badRequest().build();
        }
        Optional<DroneDTO> changedDrone = droneService.changeBatteryCapacity(serialNumber, newBatteryCapacity);
        return changedDrone
                .map(drone -> ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(drone)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}