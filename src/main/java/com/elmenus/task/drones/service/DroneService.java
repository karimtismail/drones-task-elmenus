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

@Service
public class DroneService {

    public static final int MIN_BATTERY_CAPACITY_FOR_LOADING = 25;
    public static final int MAX_MEDICATION_WEIGHT = 500;
    private final DroneRepository droneRepository;
    private final AuditLogRepository auditLogRepository;
    private final ModelMapper mapper;

    public DroneService(DroneRepository droneRepository, AuditLogRepository auditLogRepository, ModelMapper mapper) {
        this.droneRepository = droneRepository;
        this.auditLogRepository = auditLogRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Optional<DroneDTO> registerDrone(DroneDTO droneDTO) {
        Drone drone = mapper.map(droneDTO, Drone.class);
        validateDroneDTO(drone);
        drone.setState(DroneState.IDLE);
        Drone savedDrone = droneRepository.save(drone);
        return Optional.of(mapper.map(savedDrone, DroneDTO.class));
    }

    private static void validateDroneDTO(Drone drone) {
        if (drone.getBatteryCapacity() < MIN_BATTERY_CAPACITY_FOR_LOADING && drone.getState() == DroneState.LOADING) {
            throw new BatteryLowException("Drone battery is low during loading state");
        }
    }

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

    private void updateDroneWithLoadedMedications(Drone drone, Set<Medication> medications) {
        Set<DroneMedication> droneMedications = medications.stream()
                .map(medication -> new DroneMedication(drone, medication, DroneMedication.calculateTotalQuantity(drone, medication)))
                .collect(Collectors.toSet());
        drone.setDroneMedications(droneMedications);
        drone.setState(DroneState.LOADED);
    }

    private int calculateTotalWeight(Set<MedicationDTO> medications) {
        return medications.stream()
                .mapToInt(MedicationDTO::getWeight)
                .sum();
    }

    public List<DroneDTO> getAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADING);
        return drones.stream()
                .map(drone -> mapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());
    }

    public int checkDroneBatteryLevel(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return drone.getBatteryCapacity();
    }

    @Scheduled(fixedRate = 60000)
    public void checkBatteryLevels() {
        List<Drone> drones = droneRepository.findByBatteryCapacityLessThanAndStateNot(MIN_BATTERY_CAPACITY_FOR_LOADING, DroneState.LOADING);
        drones.forEach(drone -> {
            logEvent(drone, "Low Battery");
            droneRepository.save(drone);
            changeDroneState(drone.getSerialNumber(), DroneState.IDLE);
        });
    }

    public Optional<DroneDTO> changeDroneState(String serialNumber, DroneState newState) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        drone.setState(newState);
        logEvent(drone, "Changed state to " + newState);
        return Optional.of(mapper.map(droneRepository.save(drone), DroneDTO.class));
    }

    private void logEvent(Drone drone, String event) {
        AuditLog auditLog = new AuditLog();
        auditLog.setDroneId(drone.getId());
        auditLog.setDroneSerialNumber(drone.getSerialNumber());
        auditLog.setEventDescription(event);
        auditLog.setEventTimestamp(LocalDateTime.now());
        auditLogRepository.save(auditLog);
        System.out.println("Drone ID: " + drone.getId() + ", Event: " + event);
    }

    public List<AuditLog> getAuditLogEvents() {
        return auditLogRepository.findAll();
    }
}