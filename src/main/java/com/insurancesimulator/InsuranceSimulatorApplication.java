package com.insurancesimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InsuranceSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceSimulatorApplication.class, args);
    }

}
