package com.elmenus.task.drones.enums;

/**
 * Enum representing different states of drones based on their state classes.
 * The drone states provide information about the current operational state of a drone.
 *
 * @since 1.0
 */
public enum DroneState {
    /**
     * Represents the state of a drone when it is idle.
     * The drone is not actively engaged in any specific task and is available for assignments.
     */
    IDLE,

    /**
     * Represents the state of a drone when it is in the loading phase.
     * The drone is in the process of loading medications or other cargo.
     */
    LOADING,

    /**
     * Represents the state of a drone when it is loaded and ready for delivery.
     * The drone has completed the loading phase and is prepared to start its delivery mission.
     */
    LOADED,

    /**
     * Represents the state of a drone when it is actively delivering medications or cargo.
     * The drone is in transit to its destination to complete the delivery.
     */
    DELIVERING,

    /**
     * Represents the state of a drone when it has successfully delivered its payload.
     * The drone has completed its delivery mission, and the payload has been successfully delivered.
     */
    DELIVERED,

    /**
     * Represents the state of a drone when it is returning to its base after completing a mission.
     * The drone is in transit back to its home base.
     */
    RETURNING
}
