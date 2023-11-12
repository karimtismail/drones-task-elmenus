package com.elmenus.task.drones.exception;

/**
 * Exception thrown when the weight limit of a drone is exceeded.
 */
public class WeightExceededException extends RuntimeException {

    /**
     * Constructs a new WeightExceededException with the specified detail message.
     *
     * @param message the detail message.
     */
    public WeightExceededException(String message) {
        super(message);
    }
}