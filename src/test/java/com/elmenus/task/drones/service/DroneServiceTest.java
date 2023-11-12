package com.elmenus.task.drones.service;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.Drone;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.exception.BatteryLowException;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DroneService droneService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Tests for registerDrone method")
    class RegisterDroneTests {

        @Test
        @DisplayName("Should register drone successfully")
        public void shouldRegisterDroneSuccessfully() {
            // Arrange
            DroneDTO droneDTO = new DroneDTO();
            droneDTO.setBatteryCapacity(50);

            Drone drone = new Drone();
            when(mapper.map(droneDTO, Drone.class)).thenReturn(drone);
            when(droneRepository.save(drone)).thenReturn(drone);
            when(mapper.map(drone, DroneDTO.class)).thenReturn(droneDTO);

            // Act
            Optional<DroneDTO> result = droneService.registerDrone(droneDTO);

            // Assert
            verify(droneRepository, times(1)).save(drone);
            assertEquals(droneDTO, result);
        }

        @Test
        @DisplayName("Should throw BatteryLowException when battery capacity is low")
        public void shouldThrowBatteryLowExceptionWhenBatteryCapacityIsLow() {
            // Arrange
            DroneDTO droneDTO = new DroneDTO();
            droneDTO.setBatteryCapacity(20);

            // Act & Assert
            assertThrows(BatteryLowException.class, () -> droneService.registerDrone(droneDTO));
        }
    }

    @Nested
    @DisplayName("Tests for loadDroneWithMedications method")
    class LoadDroneWithMedicationsTests {

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
    }
}