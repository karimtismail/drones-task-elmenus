package com.elmenus.task.drones.entity;

import com.elmenus.task.drones.enums.DroneModel;
import com.elmenus.task.drones.enums.DroneState;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "drone")
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private DroneModel model;

    @Column(name = "weight_limit", nullable = false)
    private Integer weightLimit;

    @Column(name = "battery_capacity", nullable = false)
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private DroneState state;

    @ManyToMany
    @JoinTable(
            name = "drone_medication",
            joinColumns = @JoinColumn(name = "drone_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private Set<Medication> medications = new HashSet<>();

}
