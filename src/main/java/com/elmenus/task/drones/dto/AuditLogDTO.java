package com.elmenus.task.drones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long droneId;

    private String droneSerialNumber;

    @NotBlank(message = "Event description is required")
    private String eventDescription;

    private LocalDateTime eventTimestamp;
}
