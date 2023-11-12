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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medication")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "image", nullable = false)
    private String image;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<DroneMedication> droneMedications;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(weight, that.weight) && Objects.equals(code, that.code) && Objects.equals(image, that.image) && Objects.equals(droneMedications, that.droneMedications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, code, image);
    }

}
