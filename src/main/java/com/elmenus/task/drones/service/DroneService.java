package com.elmenus.task.drones.service;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.AuditLog;
import com.elmenus.task.drones.entity.Drone;
import com.elmenus.task.drones.entity.DroneMedication;
import com.elmenus.task.drones.entity.Medication;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.exception.BatteryLowException;
import com.elmenus.task.drones.exception.DroneStateException;
import com.elmenus.task.drones.exception.WeightExceededException;
import com.elmenus.task.drones.repository.AuditLogRepository;
import com.elmenus.task.drones.repository.DroneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing drones, including registration, loading medications, state changes, and battery level checks.
 */
@Service
public class DroneService {

    /**
     * Constant for the minimum battery capacity required for loading medications.
     */
    public static final int MIN_BATTERY_CAPACITY_FOR_LOADING = 25;

    /**
     * Constant for the maximum weight of medications that can be loaded onto a drone.
     */
    public static final int MAX_MEDICATION_WEIGHT = 500;

    private final DroneRepository droneRepository;
    private final AuditLogRepository auditLogRepository;
    private final ModelMapper mapper;

    /**
     * Constructor for the DroneService class.
     *
     * @param droneRepository    The repository for managing Drone entities.
     * @param auditLogRepository The repository for managing AuditLog entities.
     * @param mapper             The model mapper for DTO-to-entity mapping.
     */
    public DroneService(DroneRepository droneRepository, AuditLogRepository auditLogRepository, ModelMapper mapper) {
        this.droneRepository = droneRepository;
        this.auditLogRepository = auditLogRepository;
        this.mapper = mapper;
    }

    /**
     * Registers a new drone.
     *
     * @param droneDTO The DTO representing the drone to be registered.
     * @return An optional containing the registered drone as a DTO.
     */
    @Transactional
    public Optional<DroneDTO> registerDrone(DroneDTO droneDTO) {
        Drone drone = mapper.map(droneDTO, Drone.class);
        validateDroneDTO(drone);
        drone.setState(DroneState.IDLE);
        Drone savedDrone = droneRepository.save(drone);
        return Optional.of(mapper.map(savedDrone, DroneDTO.class));
    }

    /**
     * Validates a drone DTO for registration.
     *
     * @param drone The drone entity to be validated.
     */
    private static void validateDroneDTO(Drone drone) {
        if (drone.getBatteryCapacity() < MIN_BATTERY_CAPACITY_FOR_LOADING && drone.getState() == DroneState.LOADING) {
            throw new BatteryLowException("Drone battery is low during loading state");
        }
    }

    /**
     * Loads a drone with medications.
     *
     * @param serialNumber The serial number of the drone to be loaded.
     * @param medications  The set of medications to be loaded onto the drone.
     * @return An optional containing the loaded drone as a DTO.
     */
    @Transactional
    public Optional<DroneDTO> loadDroneWithMedications(String serialNumber, Set<MedicationDTO> medications) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        int weight = calculateTotalWeight(medications);
        validateDroneForLoading(drone, weight);
        Set<Medication> medicationEntities = medications.stream()
                .map(medicationDTO -> mapper.map(medicationDTO, Medication.class))
                .collect(Collectors.toSet());
        updateDroneWithLoadedMedications(drone, medicationEntities);
        droneRepository.save(drone);
        return Optional.of(mapper.map(drone, DroneDTO.class));
    }

    /**
     * Validates a drone for loading medications.
     *
     * @param drone  The drone entity to be validated.
     * @param weight The total weight of medications to be loaded.
     */
    private void validateDroneForLoading(Drone drone, int weight) {
        if (drone.getBatteryCapacity() < MIN_BATTERY_CAPACITY_FOR_LOADING) {
            throw new BatteryLowException("Battery capacity for drone is not in a valid state for loading medications");
        }
        if (weight > MAX_MEDICATION_WEIGHT) {
            throw new WeightExceededException("Weight for medication cannot exceed 500 grams for drone is not in a valid state for loading medications");
        }
        if (drone.getState() != DroneState.LOADING) {
            throw new DroneStateException("Drone is not in a valid state for loading medications");
        }
    }

    /**
     * Updates a drone with loaded medications.
     *
     * @param drone       The drone entity to be updated.
     * @param medications The set of medications to be loaded onto the drone.
     */
    private void updateDroneWithLoadedMedications(Drone drone, Set<Medication> medications) {
        Set<DroneMedication> droneMedications = medications.stream()
                .map(medication -> new DroneMedication(drone, medication, DroneMedication.calculateTotalQuantity(drone, medication)))
                .collect(Collectors.toSet());
        drone.setDroneMedications(droneMedications);
        drone.setState(DroneState.LOADED);
    }

    /**
     * Calculates the total weight of medications.
     *
     * @param medications The set of medications.
     * @return The total weight of medications.
     */
    private int calculateTotalWeight(Set<MedicationDTO> medications) {
        return medications.stream()
                .mapToInt(MedicationDTO::getWeight)
                .sum();
    }

    /**
     * Retrieves a list of available drones for loading.
     *
     * @return A list of DroneDTOs representing available drones for loading.
     */
    public List<DroneDTO> getAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADING);
        return drones.stream()
                .map(drone -> mapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Checks the battery level of a drone.
     *
     * @param serialNumber The serial number of the drone.
     * @return The battery level of the drone.
     */
    public int checkDroneBatteryLevel(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return drone.getBatteryCapacity();
    }

    /**
     * Scheduled task to check battery levels of drones.
     */
    @Scheduled(fixedRate = 60000)
    public void checkBatteryLevels() {
        List<Drone> drones = droneRepository.findByBatteryCapacityLessThanAndStateNot(MIN_BATTERY_CAPACITY_FOR_LOADING, DroneState.LOADING);
        drones.forEach(drone -> {
            logEvent(drone, "Low Battery");
            droneRepository.save(drone);
            changeDroneState(drone.getSerialNumber(), DroneState.IDLE);
        });
    }

    /**
     * Changes the state of a drone.
     *
     * @param serialNumber The serial number of the drone.
     * @param newState     The new state to set for the drone.
     * @return An optional containing the updated drone as a DTO.
     */
    public Optional<DroneDTO> changeDroneState(String serialNumber, DroneState newState) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        if (drone.getBatteryCapacity() < 25) {
            throw new BatteryLowException("Cannot change state drone when battery capacity is low");
        }
        drone.setState(newState);
        logEvent(drone, "Changed state to " + newState);
        return Optional.of(mapper.map(droneRepository.save(drone), DroneDTO.class));
    }

    /**
     * Logs an event for a drone.
     *
     * @param drone The drone entity for which the event is logged.
     * @param event The description of the event.
     */
    private void logEvent(Drone drone, String event) {
        AuditLog auditLog = new AuditLog();
        auditLog.setDroneId(drone.getId());
        auditLog.setDroneSerialNumber(drone.getSerialNumber());
        auditLog.setEventDescription(event);
        auditLog.setEventTimestamp(LocalDateTime.now());
        auditLogRepository.save(auditLog);
        System.out.println("Drone ID: " + drone.getId() + ", Event: " + event);
    }

    /**
     * Retrieves a list of audit log events.
     *
     * @return A list of AuditLog entities representing audit log events.
     */
    public List<AuditLog> getAuditLogEvents() {
        return auditLogRepository.findAll();
    }
}
