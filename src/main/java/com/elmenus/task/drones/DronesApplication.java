package com.elmenus.task.drones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main application class for managing drones.
 *
 * <p>This class uses Spring Boot and is responsible for configuring and starting the drone management system.
 * It enables scheduling for background tasks related to drone management.</p>
 *
 * @see SpringApplication
 * @see SpringBootApplication
 * @see EnableScheduling
 */
@SpringBootApplication
@EnableScheduling
public class DronesApplication {

    /**
     * The entry point for the drone management application.
     *
     * <p>This method starts the Spring Boot application.</p>
     *
     * @param args The command line arguments passed to the application.
     * @see SpringApplication#run(Class, String...)
     */
    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }

}
