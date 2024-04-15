package com.pcrs.helpdesk.config;

import com.pcrs.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;


    @Bean
    public Boolean instanciaDB() {
        this.dbService.instanciaDB();
        return true;
    }
}
