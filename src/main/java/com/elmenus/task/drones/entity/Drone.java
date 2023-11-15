package com.elmenus.task.drones.entity;

import com.elmenus.task.drones.shared.enums.DroneModel;
import com.elmenus.task.drones.shared.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class representing a drone.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone")
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the drone.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Serial number of the drone.
     */
    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    /**
     * Model of the drone.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private DroneModel model;

    /**
     * Weight limit of the drone.
     */
    @Column(name = "weight_limit", nullable = false)
    private Integer weightLimit;

    /**
     * Battery capacity of the drone.
     */
    @Column(name = "battery_capacity", nullable = false)
    private Integer batteryCapacity;

    /**
     * State of the drone.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private DroneState state;

    /**
     * Set of drone medications associated with the drone.
     */
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<DroneMedication> droneMedications;

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id) &&
                Objects.equals(serialNumber, drone.serialNumber) &&
                model == drone.model &&
                Objects.equals(weightLimit, drone.weightLimit) &&
                Objects.equals(batteryCapacity, drone.batteryCapacity) &&
                state == drone.state &&
                Objects.equals(droneMedications, drone.droneMedications);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, weightLimit, batteryCapacity, state);
    }
}