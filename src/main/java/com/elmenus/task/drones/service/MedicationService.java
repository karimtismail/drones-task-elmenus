package com.elmenus.task.drones.service;

import com.elmenus.task.drones.dto.MedicationDTO;
import com.elmenus.task.drones.entity.Medication;
import com.elmenus.task.drones.repository.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final ModelMapper mapper;


    @Autowired
    public MedicationService(MedicationRepository medicationRepository, ModelMapper mapper) {
        this.medicationRepository = medicationRepository;
        this.mapper = mapper;
    }

    public List<MedicationDTO> getLoadedMedicationsForDrone(String serialNumber) {
        List<Medication> loadedMedications = medicationRepository.findLoadedMedicationsForDrone(serialNumber);
        return loadedMedications.stream()
                .map(medication -> mapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }
}
