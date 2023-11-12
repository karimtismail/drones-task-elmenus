package com.elmenus.task.drones.service;

import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.Medication;
import com.elmenus.task.drones.repository.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing medication-related operations.
 */
@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final ModelMapper mapper;

    /**
     * Constructor for the MedicationService class.
     *
     * @param medicationRepository The repository for managing Medication entities.
     * @param mapper               The model mapper for entity-to-DTO mapping.
     */
    @Autowired
    public MedicationService(MedicationRepository medicationRepository, ModelMapper mapper) {
        this.medicationRepository = medicationRepository;
        this.mapper = mapper;
    }

    /**
     * Retrieves a list of loaded medications for a given drone.
     *
     * @param serialNumber The serial number of the drone.
     * @return A list of MedicationDTOs representing loaded medications for the drone.
     */
    public List<MedicationDTO> getLoadedMedicationsForDrone(String serialNumber) {
        List<Medication> loadedMedications = medicationRepository.findLoadedMedicationsForDrone(serialNumber);
        return loadedMedications.stream()
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }
}
