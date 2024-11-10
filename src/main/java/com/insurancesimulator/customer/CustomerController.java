package com.insurancesimulator.customer;

import com.insurancesimulator.customer.model.Customer;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> importCustomers(){
        return customerService.getCustomers();
    }


    @PostMapping
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomerInfo(@PathVariable("customerId") Long customerId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) LocalDate birthDate){
        customerService.updateCustomer(customerId, name, birthDate);
    }
}
