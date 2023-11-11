package com.elmenus.task.drones.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToMany(mappedBy = "medications")
    private Set<Drone> drones = new HashSet<>();
}
