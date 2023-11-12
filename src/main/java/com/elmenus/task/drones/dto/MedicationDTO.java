package com.elmenus.task.drones.dto;


import com.elmenus.task.drones.entity.DroneMedication;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Name must contain only letters, numbers, '-', and '_'")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Column(name = "weight")
    @PositiveOrZero(message = "Weight must be a positive or zero value")
    private Integer weight;

    @NotEmpty(message = "Code cannot be empty")
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "Code must contain only upper case letters, numbers, and '_'")
    @Size(max = 50, message = "Code cannot exceed 50 characters")
    private String code;

    @Column(name = "image")
    @NotEmpty(message = "Image cannot be empty")
    private String image;

    private Set<DroneMedication> droneMedications;

}
