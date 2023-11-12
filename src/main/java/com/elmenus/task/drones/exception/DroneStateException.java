package com.elmenus.task.drones.exception;

/**
 * Exception thrown when a drone is in an invalid state for a particular operation.
 */
public class DroneStateException extends RuntimeException {

    /**
     * Constructs a new DroneStateException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DroneStateException(String message) {
        super(message);
    }
}