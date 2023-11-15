package com.elmenus.task.drones.shared.enums;

/**
 * Enum representing different models of drones based on their weight classes.
 * The drone models are categorized into four weight classes: LIGHTWEIGHT, MIDDLEWEIGHT, CRUISERWEIGHT, and HEAVYWEIGHT.
 * These weight classes help in classifying drones based on their size, capacity, and use cases.
 *
 * @since 1.0
 */
public enum DroneModel {
    /**
     * Represents a lightweight drone model.
     * Lightweight drones are typically smaller and have lower carrying capacities.
     */
    LIGHTWEIGHT,

    /**
     * Represents a middleweight drone model.
     * Middleweight drones have a moderate size and carrying capacity.
     */
    MIDDLEWEIGHT,

    /**
     * Represents a cruiserweight drone model.
     * Cruiserweight drones are larger and capable of carrying heavier loads.
     */
    CRUISERWEIGHT,

    /**
     * Represents a heavyweight drone model.
     * Heavyweight drones are the largest in size and can carry substantial payloads.
     */
    HEAVYWEIGHT
}
