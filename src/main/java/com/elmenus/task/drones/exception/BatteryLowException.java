package com.elmenus.task.drones.exception;

/**
 * Exception thrown when a drone's battery level is low.
 */
public class BatteryLowException extends RuntimeException {

    /**
     * Constructs a new BatteryLowException with the specified detail message.
     *
     * @param message the detail message.
     */
    public BatteryLowException(String message) {
        super(message);
    }
}

