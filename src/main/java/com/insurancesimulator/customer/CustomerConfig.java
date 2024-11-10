package com.insurancesimulator.customer;

import com.insurancesimulator.customer.model.Customer;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
            Customer maria = new Customer(
                1L,
                "Maria",
                LocalDate.of(1988, Month.APRIL, 10),
                true);

            Customer anna = new Customer(
                2L,
                "Anna",
                LocalDate.of(1990, Month.MAY, 10),
                true);
            repository.saveAll(List.of(maria, anna));
        };
    }

}
