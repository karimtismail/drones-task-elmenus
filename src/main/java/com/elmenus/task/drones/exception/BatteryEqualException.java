package com.elmenus.task.drones.exception;

/**
 * Exception thrown when a drone's battery level is equal.
 */
public class BatteryEqualException extends RuntimeException {
    /**
     * Constructs a new BatteryEqualException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BatteryEqualException(String message) {
        super(message);
    }
}
