package com.elmenus.task.drones.controller;

import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping("/loaded/{serialNumber}")
    public ResponseEntity<List<MedicationDTO>> getLoadedMedicationsForDrone(@PathVariable String serialNumber) {
        List<MedicationDTO> loadedMedications = medicationService.getLoadedMedicationsForDrone(serialNumber);
        return new ResponseEntity<>(loadedMedications, HttpStatus.OK);
    }
}
