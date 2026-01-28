package org.neueda.rest.controller;

import org.neueda.rest.entity.Customer;
import org.neueda.rest.exception.InvalidParamsException;
import org.neueda.rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @PostMapping("/")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        if (customer == null || customer.getName() == null || customer.getName().isBlank() || customer.getEmail()==null || customer.getEmail().isBlank()) {
            throw new InvalidParamsException("Customer name and Email must be provided");
        }
        Customer savedCustomer=service.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers(){

        List<Customer> customers=service.getAllCustomers();
       // return  new ResponseEntity<>(customers, HttpStatus.OK);
        return ResponseEntity.ok(customers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id){

        Customer customer=service.getCustomerById(id);
        return  ResponseEntity.ok(customer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id){

        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer){
        customer.setId(id);
        Customer updatedCustomer=service.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/by-email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        if (email == null) {
            throw new InvalidParamsException("Customer Email must be provided");
        }
        return ResponseEntity.ok(service.getCustomerByEmail(email));
    }

}
