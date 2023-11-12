package com.elmenus.task.drones.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) class for representing medication information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Name of the medication.
     */
    @NotBlank(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Name must contain only letters, numbers, '-', and '_'")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    /**
     * Weight of the medication.
     */
    @Column(name = "weight")
    @PositiveOrZero(message = "Weight must be a positive or zero value")
    private Integer weight;

    /**
     * Code associated with the medication.
     */
    @NotEmpty(message = "Code cannot be empty")
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "Code must contain only upper case letters, numbers, and '_'")
    @Size(max = 50, message = "Code cannot exceed 50 characters")
    private String code;

    /**
     * Image representing the medication.
     */
    @Column(name = "image")
    @NotEmpty(message = "Image cannot be empty")
    private String image;
}
