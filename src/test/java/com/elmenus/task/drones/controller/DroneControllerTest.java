package com.elmenus.task.drones.controller;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.AuditLog;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.service.DroneService;
import com.elmenus.task.drones.utility.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link DroneController} class.
 */
class DroneControllerTest {

    private DroneService droneService;
    private DroneController droneController;

    /**
     * Set up the test environment by initializing mock objects.
     */
    @BeforeEach
    void setUp() {
        droneService = mock(DroneService.class);
        droneController = new DroneController(droneService);
    }

    /**
     * Test for registering a new drone with a valid DroneDTO, expecting a created response.
     */
    @Test
    void registerDrone_ValidDroneDTO_ReturnsCreatedResponse() {
        DroneDTO droneDTO = new DroneDTO();
        when(droneService.registerDrone(droneDTO)).thenReturn(Optional.of(droneDTO));

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.registerDrone(droneDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(droneDTO, response.getBody().getData());
    }

    /**
     * Test for registering a new drone with an invalid DroneDTO, expecting a bad request response.
     */
    @Test
    void registerDrone_InvalidDroneDTO_ReturnsBadRequestResponse() {
        DroneDTO droneDTO = new DroneDTO();
        when(droneService.registerDrone(droneDTO)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.registerDrone(droneDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test for loading a drone with medications with valid input, expecting an OK response.
     */
    @Test
    void loadDroneWithMedications_ValidInput_ReturnsOkResponse() {
        String serialNumber = "123";
        Set<MedicationDTO> medications = new HashSet<>();
        medications.add(MedicationDTO.builder()
                .name("MED_1")
                .image("IMAGE_MED_1")
                .code("CODE_MED_1")
                .weight(124)
                .build());
        medications.add(MedicationDTO.builder()
                .name("MED_2")
                .image("IMAGE_MED_2")
                .code("CODE_MED_2")
                .weight(31)
                .build());
        DroneDTO droneDTO = new DroneDTO();
        when(droneService.loadDroneWithMedications(serialNumber, medications)).thenReturn(Optional.of(droneDTO));

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.loadDroneWithMedications(serialNumber, medications);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(droneDTO, response.getBody().getData());
    }

    /**
     * Test for retrieving available drones for loading, expecting an OK response.
     */
    @Test
    void getAvailableDronesForLoading_ReturnsOkResponse() {
        List<DroneDTO> availableDrones = List.of(new DroneDTO(), new DroneDTO());
        when(droneService.getAvailableDronesForLoading()).thenReturn(availableDrones);

        ResponseEntity<ApiResponse<List<DroneDTO>>> response = droneController.getAvailableDronesForLoading();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availableDrones, response.getBody().getData());
    }

    /**
     * Test for retrieving the battery level of a drone, expecting an OK response.
     */
    @Test
    void getDroneBatteryLevel_ReturnsOkResponse() {
        String serialNumber = "123";
        int batteryLevel = 75;
        when(droneService.checkDroneBatteryLevel(serialNumber)).thenReturn(batteryLevel);

        ResponseEntity<ApiResponse<Integer>> response = droneController.getDroneBatteryLevel(serialNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(batteryLevel, response.getBody().getData());
    }

    /**
     * Test for retrieving audit log events, expecting an OK response.
     */
    @Test
    void getAuditLogEvents_ReturnsOkResponse() {
        List<AuditLog> auditLogEvents = List.of(new AuditLog(), new AuditLog());
        when(droneService.getAuditLogEvents()).thenReturn(auditLogEvents);

        ResponseEntity<ApiResponse<List<AuditLog>>> response = droneController.getAuditLogEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auditLogEvents, response.getBody().getData());
    }

    /**
     * Test for changing the state of a drone with valid input, expecting an OK response.
     */
    @Test
    void changeDroneState_ValidInput_ReturnsOkResponse() {
        String serialNumber = "123";
        DroneState newState = DroneState.IDLE;
        DroneDTO changedDroneDTO = new DroneDTO();
        when(droneService.changeDroneState(serialNumber, newState)).thenReturn(Optional.of(changedDroneDTO));

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.changeDroneState(serialNumber, newState);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(changedDroneDTO, response.getBody().getData());
    }

    /**
     * Test for changing the state of a drone with invalid input, expecting a not found response.
     */
    @Test
    void changeDroneState_InvalidInput_ReturnsNotFoundResponse() {
        String serialNumber = "123";
        DroneState newState = DroneState.IDLE;
        when(droneService.changeDroneState(serialNumber, newState)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.changeDroneState(serialNumber, newState);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test for changing the battery capacity of a drone with valid input, expecting an OK response.
     */
    @Test
    void changeBatteryCapacity_ValidInput_ReturnsOkResponse() {
        String serialNumber = "123";
        int newBatteryCapacity = 80;
        DroneDTO changedDroneDTO = new DroneDTO();
        when(droneService.changeBatteryCapacity(serialNumber, newBatteryCapacity)).thenReturn(Optional.of(changedDroneDTO));

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.changeBatteryCapacity(serialNumber, newBatteryCapacity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(changedDroneDTO, response.getBody().getData());
    }

    /**
     * Test for changing the battery capacity of a drone with invalid input, expecting a bad request response.
     */
    @Test
    void changeBatteryCapacity_InvalidInput_ReturnsBadRequestResponse() {
        String serialNumber = "123";
        int newBatteryCapacity = 120;
        when(droneService.changeBatteryCapacity(serialNumber, newBatteryCapacity)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<DroneDTO>> response = droneController.changeBatteryCapacity(serialNumber, newBatteryCapacity);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}