package com.elmenus.task.drones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing an audit log entry.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_log")
public class AuditLog {
    /**
     * Unique identifier for the audit log entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID of the drone associated with the audit log entry.
     */
    @Column(name = "drone_id", nullable = false)
    private Integer droneId;

    /**
     * Serial number of the drone associated with the audit log entry.
     */
    @Column(name = "drone_serial_number", nullable = false, length = 100)
    private String droneSerialNumber;

    /**
     * Description of the event recorded in the audit log.
     */
    @Column(name = "event_description", nullable = false)
    private String eventDescription;

    /**
     * Timestamp when the audit log event occurred.
     */
    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime eventTimestamp;
}
