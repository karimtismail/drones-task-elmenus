package com.elmenus.task.drones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "drone_id", nullable = false)
    private Integer droneId;

    @Column(name = "drone_serial_number", nullable = false, length = 100)
    private String droneSerialNumber;

    @Column(name = "event_description", nullable = false)
    private String eventDescription;

    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime eventTimestamp;
}
