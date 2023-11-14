package com.elmenus.task.drones.controller;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DroneControllerTest {

    private DroneService droneService;
    private DroneController droneController;

    @BeforeEach
    void setUp() {
        droneService = mock(DroneService.class);
        droneController = new DroneController(droneService);
    }

    @Test
    void registerDrone_ValidDroneDTO_ReturnsCreatedResponse() {
        DroneDTO droneDTO = new DroneDTO();
        when(droneService.registerDrone(droneDTO)).thenReturn(Optional.of(droneDTO));

        ResponseEntity<DroneDTO> response = droneController.registerDrone(droneDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(droneDTO, response.getBody());
    }

    @Test
    void registerDrone_InvalidDroneDTO_ReturnsBadRequestResponse() {
        DroneDTO droneDTO = new DroneDTO();
        when(droneService.registerDrone(droneDTO)).thenReturn(Optional.empty());

        ResponseEntity<DroneDTO> response = droneController.registerDrone(droneDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void loadDroneWithMedications_ValidInput_ReturnsOkResponse() {
        String serialNumber = "123";
        Set<MedicationDTO> medications = new HashSet<>();
        DroneDTO droneDTO = new DroneDTO();
        when(droneService.loadDroneWithMedications(serialNumber, medications)).thenReturn(Optional.of(droneDTO));

        ResponseEntity<DroneDTO> response = droneController.loadDroneWithMedications(serialNumber, medications);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(droneDTO, response.getBody());
    }
}