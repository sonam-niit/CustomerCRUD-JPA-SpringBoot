package org.neueda.rest.service;

import org.neueda.rest.entity.Customer;
import org.neueda.rest.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo repo;

    public Customer save(Customer customer) {
        return repo.save(customer);
    }
    public List<Customer> getAllCustomers(){
        return  repo.findAll();
    }
    public Customer getCustomerById(Integer id){
        return  repo.findById(id).orElseThrow(()-> new RuntimeException("Customer Not Found"));
    }
    public void deleteCustomer(Integer id){
        repo.deleteById(id);
    }
    //update by id--> we will use Save Method only

    public Customer getCustomerByEmail(String email) {
        return repo
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
    }
}
