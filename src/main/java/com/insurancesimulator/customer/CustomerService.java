package com.insurancesimulator.customer;

import com.insurancesimulator.customer.model.Customer;
import exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId){
        boolean exists = customerRepository.existsById(customerId);
        if (!exists) {
            throw new EntityNotFoundException(Customer.class);
        }
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId, String name, LocalDate birthDate){
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class));

        if (name != null && name.length() > 0 && !Objects.equals(customer.getName(), name)) {
            customer.setName(name);
        }

        if (birthDate != null && !Objects.equals(customer.getBirthDate(), birthDate)) {
            customer.setBirthDate(birthDate);
        }
    }

}
