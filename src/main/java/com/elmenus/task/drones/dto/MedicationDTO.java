package com.elmenus.task.drones.dto;


import com.elmenus.task.drones.entity.Drone;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class MedicationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Column(name = "weight")
    @Min(value = 1, message = "Weight must be greater than 0")
    private Integer weight;

    @NotBlank(message = "Code is mandatory")
    @Size(max = 50, message = "Code cannot exceed 50 characters")
    private String code;

    @Column(name = "image")
    @NotBlank(message = "Image URL is mandatory")
    private String image;

    private Set<Drone> drones = new HashSet<>();
}
