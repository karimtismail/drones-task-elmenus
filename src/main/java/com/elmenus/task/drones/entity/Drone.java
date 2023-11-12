package com.elmenus.task.drones.entity;

import com.elmenus.task.drones.enums.DroneModel;
import com.elmenus.task.drones.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone")
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "serial_number", nullable = false, length = 100)
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

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<DroneMedication> droneMedications;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id) && Objects.equals(serialNumber, drone.serialNumber) && model == drone.model && Objects.equals(weightLimit, drone.weightLimit) && Objects.equals(batteryCapacity, drone.batteryCapacity) && state == drone.state && Objects.equals(droneMedications, drone.droneMedications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, weightLimit, batteryCapacity, state);
    }

}
