package com.elmenus.task.drones.dto;


import com.elmenus.task.drones.entity.Medication;
import com.elmenus.task.drones.enums.DroneModel;
import com.elmenus.task.drones.enums.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class DroneDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size(max = 100, message = "Serial number cannot exceed 100 characters")
    @NotBlank(message = "Serial Number is mandatory field")
    private String serialNumber;

    @NotNull(message = "Drone model is mandatory field")
    private DroneModel model;

    @NotNull(message = "Weight limit is mandatory field")
    @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
    private Integer weightLimit;

    @NotNull(message = "Battery capacity is mandatory field")
    private Integer batteryCapacity;

    @NotNull(message = "Drone state is mandatory field")
    private DroneState state;

    private Set<Medication> medications = new HashSet<>();

}
