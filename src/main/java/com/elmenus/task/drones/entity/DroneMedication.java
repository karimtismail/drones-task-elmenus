package com.elmenus.task.drones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Entity class representing the association between a drone and a medication, including quantity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone_medication")
public class DroneMedication {

    /**
     * Unique identifier for the drone-medication association.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The drone associated with the medication.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    /**
     * The medication associated with the drone.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    /**
     * The quantity of the medication loaded onto the drone.
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * Constructor for creating a new DroneMedication instance.
     *
     * @param drone      The drone associated with the medication.
     * @param medication The medication associated with the drone.
     * @param quantity   The quantity of the medication loaded onto the drone.
     */
    public DroneMedication(Drone drone, Medication medication, Integer quantity) {
        this.drone = drone;
        this.medication = medication;
        this.quantity = quantity;
    }

    /**
     * Calculates the total quantity of a specific medication loaded onto all drones.
     *
     * @param drone      The drone associated with the medication.
     * @param medication The medication for which to calculate the total quantity.
     * @return The total quantity of the specified medication loaded onto all drones.
     */
    public static Integer calculateTotalQuantity(Drone drone, Medication medication) {
        return drone.getDroneMedications().stream()
                .filter(dm -> dm.getMedication().equals(medication))
                .mapToInt(DroneMedication::getQuantity)
                .sum();
    }

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
        DroneMedication that = (DroneMedication) o;
        return Objects.equals(id, that.id) && Objects.equals(drone, that.drone) && Objects.equals(medication, that.medication) && Objects.equals(quantity, that.quantity);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, drone, medication, quantity);
    }
}
