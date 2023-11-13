package com.elmenus.task.drones.exception;

/**
 * Exception thrown when a drone's battery level is high.
 */
public class BatteryHighException extends RuntimeException {
    /**
     * Constructs a new BatteryHighException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BatteryHighException(String message) {
        super(message);
    }
}
