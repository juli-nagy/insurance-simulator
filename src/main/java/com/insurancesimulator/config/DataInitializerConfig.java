package com.insurancesimulator.config;

import com.insurancesimulator.customer.CustomerRepository;
import com.insurancesimulator.customer.model.Customer;
import com.insurancesimulator.insurance.InsuranceRepository;
import com.insurancesimulator.insurance.model.CarInsurance;
import com.insurancesimulator.insurance.model.LifeInsurance;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class DataInitializerConfig {

    @Bean
    CommandLineRunner dataInitializer(CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        return args -> {
            // Initialize Customer data
            Customer maria = new Customer(
                1L,
                "Maria",
                LocalDate.of(1988, Month.APRIL, 10),
                true
            );

            Customer anna = new Customer(
                2L,
                "Anna",
                LocalDate.of(1990, Month.MAY, 10),
                true
            );

            customerRepository.saveAll(List.of(maria, anna));

            Long mariaId = customerRepository.findByName("Maria").orElseThrow().getCustomerId();
            Long annaId = customerRepository.findByName("Anna").orElseThrow().getCustomerId();

            // Initialize Insurance data
            LifeInsurance lifeInsurance = LifeInsurance.builder()
                .coverageAmount(100000.0)
                .balance(100000.0)
                .customerId(annaId)
                .premiumPayment(150.0)
                .policyTermInYears(20)
                .isActive(true)
                .build();

            CarInsurance carInsurance = CarInsurance.builder()
                .coverageAmount(20000.0)
                .balance(1500.75)
                .customerId(mariaId)
                .premiumPayment(400.0)
                .carCost(60000)
                .isActive(true)
                .build();

            insuranceRepository.saveAll(List.of(lifeInsurance, carInsurance));
        };
    }
}

