package com.elmenus.task.drones.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class representing a medication that can be loaded onto drones.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medication")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the medication.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the medication.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The weight of the medication.
     */
    @Column(name = "weight", nullable = false)
    private Integer weight;

    /**
     * The code associated with the medication.
     */
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * The image URL or path representing the medication.
     */
    @Column(name = "image", nullable = false)
    private String image;

    /**
     * Set of associations between this medication and drones, specifying the quantity loaded onto each drone.
     */
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL)
    @JsonBackReference
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
        Medication that = (Medication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(weight, that.weight) && Objects.equals(code, that.code) && Objects.equals(image, that.image) && Objects.equals(droneMedications, that.droneMedications);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, code, image);
    }
}