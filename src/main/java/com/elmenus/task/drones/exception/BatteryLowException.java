package com.elmenus.task.drones.exception;

public class BatteryLowException extends RuntimeException {
    public BatteryLowException(String message) {
        super(message);
    }
}
