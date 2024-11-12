package com.insurancesimulator.customer;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import com.insurancesimulator.customer.model.Customer;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer entity was created");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> importCustomers(){
        List<Customer> foundCustomers = customerService.getCustomers();
        return foundCustomers.isEmpty() ? new ResponseEntity<>(NOT_FOUND)
            : ResponseEntity.ok(foundCustomers);
    }

    @PutMapping(path = "{customerId}")
    public ResponseEntity<String> updateCustomerInfo(@PathVariable("customerId") Long customerId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) LocalDate birthDate){
        customerService.updateCustomer(customerId, name, birthDate);
        return ResponseEntity.ok().body("Customer entity was updated");
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().body("Customer entity was deleted");
    }
}
