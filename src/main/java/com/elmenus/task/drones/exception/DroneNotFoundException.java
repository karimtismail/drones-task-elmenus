package com.elmenus.task.drones.exception;

/**
 * Exception thrown when a drone not found.
 */
public class DroneNotFoundException extends RuntimeException {
    /**
     * Constructs a new DroneNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DroneNotFoundException(String message) {
        super(message);
    }
}
