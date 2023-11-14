package com.elmenus.task.drones.service;

import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.Drone;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.exception.DroneNotFoundException;
import com.elmenus.task.drones.exception.DroneStateException;
import com.elmenus.task.drones.exception.WeightExceededException;
import com.elmenus.task.drones.repository.AuditLogRepository;
import com.elmenus.task.drones.repository.DroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link DroneService} class.
 */
public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DroneService droneService;

    /**
     * Setup method to initialize mock objects.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Nested class containing tests for the {@code loadDroneWithMedications} method.
     */
    @Nested
    @DisplayName("Tests for loadDroneWithMedications method")
    class LoadDroneWithMedicationsTests {

        /**
         * Test case: Should throw WeightExceededException when weight limit is exceeded.
         */
        @Test
        @DisplayName("Should throw WeightExceededException when weight limit is exceeded")
        public void shouldThrowWeightExceededExceptionWhenWeightLimitIsExceeded() {
            // Arrange
            String serialNumber = "123";
            Set<MedicationDTO> medications = new HashSet<>();
            medications.add(MedicationDTO.builder()
                    .name("Medicine1")
                    .weight(300)
                    .code("Code123")
                    .image("ImageURL")
                    .build());
            medications.add(MedicationDTO.builder()
                    .name("Medicine2")
                    .weight(300)
                    .code("Code124")
                    .image("ImageURL")
                    .build());

            Drone drone = new Drone();
            drone.setBatteryCapacity(50);
            drone.setState(DroneState.LOADING);

            when(droneRepository.findBySerialNumber(serialNumber)).thenReturn(drone);

            // Act & Assert
            assertThrows(WeightExceededException.class, () -> droneService.loadDroneWithMedications(serialNumber, medications));
        }

        /**
         * Test case: Should throw DroneStateException when drone is not in the LOADING state.
         */
        @Test
        @DisplayName("Should throw DroneStateException when drone is not in the LOADING state")
        public void shouldDroneStateExceptionWhenDroneNotInLoadingState() {
            // Arrange
            String serialNumber = "123";
            Set<MedicationDTO> medications = new HashSet<>();
            medications.add(MedicationDTO.builder()
                    .name("Medicine1")
                    .weight(100)
                    .code("Code123")
                    .image("ImageURL")
                    .build());

            Drone drone = new Drone();
            drone.setBatteryCapacity(50);
            drone.setState(DroneState.LOADED);

            when(droneRepository.findBySerialNumber(serialNumber)).thenReturn(drone);

            // Act & Assert
            assertThrows(DroneStateException.class, () -> droneService.loadDroneWithMedications(serialNumber, medications));
        }

        /**
         * Test case: Should throw DroneNotFoundException when drone with given serial number does not exist.
         */
        @Test
        @DisplayName("Should throw DroneNotFoundException when drone with given serial number does not exist")
        public void shouldThrowDroneNotFoundExceptionWhenDroneNotFound() {
            // Arrange
            String serialNumber = "123";
            Set<MedicationDTO> medications = new HashSet<>();
            medications.add(MedicationDTO.builder()
                    .name("Medicine1")
                    .weight(100)
                    .code("Code123")
                    .image("ImageURL")
                    .build());

            when(droneRepository.findBySerialNumber(serialNumber)).thenReturn(null);

            // Act & Assert
            assertThrows(DroneNotFoundException.class, () -> droneService.loadDroneWithMedications(serialNumber, medications));
        }
    }
}