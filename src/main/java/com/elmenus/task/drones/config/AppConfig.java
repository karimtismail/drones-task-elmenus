package com.elmenus.task.drones.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for application-wide configurations.
 */
@Configuration
public class AppConfig {
    /**
     * Bean definition for ModelMapper, a mapping library.
     *
     * @return An instance of ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
