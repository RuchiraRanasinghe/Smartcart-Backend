package smartcart.org.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartcart.org.dto.CustomerDto;
import smartcart.org.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
        CustomerDto created = customerService.createCustomer(customer);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        CustomerDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto customer) {
        CustomerDto updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        if (Boolean.TRUE.equals(customerService.deleteCustomer(id))) {
            return ResponseEntity.status(201).body("Customer deleted successfully");
        }
        return ResponseEntity.status(404).body("Customer not found");
    }
}
