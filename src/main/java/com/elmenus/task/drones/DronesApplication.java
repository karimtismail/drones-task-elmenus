package com.elmenus.task.drones;

import com.elmenus.task.drones.dto.DroneDTO;
import com.elmenus.task.drones.enums.DroneModel;
import com.elmenus.task.drones.enums.DroneState;
import com.elmenus.task.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }

    @Autowired
    private DroneService droneService;

    @Override
    public void run(String... args) throws Exception {
        DroneDTO droneDTO = new DroneDTO();
        droneDTO.setState(DroneState.LOADING);
        droneDTO.setModel(DroneModel.CRUISERWEIGHT);
        droneDTO.setSerialNumber("DR_123853");
        droneDTO.setBatteryCapacity(20);
        droneDTO.setWeightLimit(200);

//        droneService.registerDrone(droneDTO);
    }
}
