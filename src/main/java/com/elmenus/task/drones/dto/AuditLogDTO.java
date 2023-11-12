package com.elmenus.task.drones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) class for representing audit log information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the audit log entry.
     */
    private Long id;

    /**
     * Identifier of the associated drone.
     */
    private Long droneId;

    /**
     * Serial number of the associated drone.
     */
    private String droneSerialNumber;

    /**
     * Description of the event captured in the audit log.
     */
    @NotBlank(message = "Event description is required")
    private String eventDescription;

    /**
     * Timestamp indicating when the event occurred.
     */
    private LocalDateTime eventTimestamp;
}