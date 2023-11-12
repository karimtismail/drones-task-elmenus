package com.elmenus.task.drones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone_medication")
public class DroneMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public DroneMedication(Drone drone, Medication medication, Integer quantity) {
        this.drone = drone;
        this.medication = medication;
        this.quantity = quantity;
    }

    public static Integer calculateTotalQuantity(Drone drone, Medication medication) {
        return drone.getDroneMedications().stream()
                .filter(dm -> dm.getMedication().equals(medication))
                .mapToInt(DroneMedication::getQuantity)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneMedication that = (DroneMedication) o;
        return Objects.equals(id, that.id) && Objects.equals(drone, that.drone) && Objects.equals(medication, that.medication) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drone, medication, quantity);
    }
}
