package com.elmenus.task.drones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneMedicationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Drone ID is required")
    private Long droneId;

    @NotNull(message = "Medication ID is required")
    private Long medicationId;

    private Integer quantity;
}
