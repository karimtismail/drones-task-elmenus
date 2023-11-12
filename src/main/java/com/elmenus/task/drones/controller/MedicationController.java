package com.elmenus.task.drones.controller;

import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for managing medication-related operations.
 */
@Validated
@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService medicationService;

    /**
     * Constructor for MedicationController.
     *
     * @param medicationService The service responsible for handling medication-related operations.
     */
    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * Endpoint for retrieving loaded medications for a specific drone.
     *
     * @param serialNumber The serial number of the drone.
     * @return ResponseEntity with the list of loaded medication DTOs and HTTP status.
     */
    @GetMapping("/loaded/{serialNumber}")
    public ResponseEntity<List<MedicationDTO>> getLoadedMedicationsForDrone(@PathVariable String serialNumber) {
        List<MedicationDTO> loadedMedications = medicationService.getLoadedMedicationsForDrone(serialNumber);
        return new ResponseEntity<>(loadedMedications, HttpStatus.OK);
    }
}
