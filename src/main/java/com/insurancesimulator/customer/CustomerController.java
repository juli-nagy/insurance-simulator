package com.insurancesimulator.customer;

import com.insurancesimulator.customer.model.Customer;
import com.insurancesimulator.customer.model.CustomerListResponse;
import com.insurancesimulator.customer.model.CustomerUpdateRequest;
import com.insurancesimulator.utils.models.shared.InsuranceSimulatorResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<InsuranceSimulatorResponse> registerNewCustomer(
        @RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                new InsuranceSimulatorResponse("Customer entity was created", HttpStatus.CREATED));
    }

    @GetMapping
    public ResponseEntity<CustomerListResponse> importCustomers() {
        List<Customer> foundCustomers = customerService.getCustomers();
        return ResponseEntity.ok(
            new CustomerListResponse(
                "Customers found", foundCustomers.size(), foundCustomers)
        );
    }

    @PutMapping(path = "{customerId}")
    public ResponseEntity<InsuranceSimulatorResponse> updateCustomerInfo(
        @PathVariable("customerId") Long customerId,
        @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest.getName(),
            updateRequest.getBirthDate());
        return ResponseEntity.ok(new InsuranceSimulatorResponse(
            "Customer entity was updated", HttpStatus.OK)
        );
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<InsuranceSimulatorResponse> deleteCustomer(
        @PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(
            new InsuranceSimulatorResponse("Customer entity was deleted", HttpStatus.OK)
        );
    }

}
