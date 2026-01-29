package org.neueda.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neueda.rest.entity.Customer;
import org.neueda.rest.exception.CustomerNotFoundException;
import org.neueda.rest.repo.CustomerRepo;
import org.neueda.rest.service.CustomerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        List<Customer> mockCustomers = Arrays.asList(
                new Customer("Alex", "alex@gmail.com"),
                new Customer("Bob", "bob@gmail.com")
        );
        Mockito.when(customerRepo.findAll()).thenReturn(mockCustomers);
        List<Customer> customers = customerService.getAllCustomers();
        Assertions.assertEquals(2, customers.size());
    }

    @Test
    void testGetCustomerById_found() {
        Customer mockCustomer = new Customer("Alex", "alex@gmail.com");
        Mockito.when(customerRepo.findById(1)).thenReturn(Optional.of(mockCustomer));
        Customer customer = customerService.getCustomerById(1);
        Assertions.assertEquals("Alex", customer.getName());
    }

    @Test
    void testGetCustomerById_notFound() {
        Mockito.when(customerRepo.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(2));
    }

    @Test
    void testCreateCustomer() {
        Customer toSave = new Customer("Krizel", "kriss@gmail.com");
        Mockito.when(customerRepo.save(toSave)).thenReturn(toSave);
        Customer saved = customerService.save(toSave);
        Assertions.assertEquals("Krizel", saved.getName());
    }
//
//    @Test
//    void testUpdateItem_found() {
//        Item existing = new Item("Apple", 0.99);
//        Item update = new Item("Green Apple", 1.09);
//        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(existing));
//        Mockito.when(itemRepository.save(existing)).thenReturn(existing);
//        Item updated = itemService.updateItem(1L, update);
//        Assertions.assertEquals("Green Apple", updated.getName());
//        Assertions.assertEquals(1.09, updated.getPrice());
//    }
//
//    @Test
//    void testUpdateItem_notFound() {
//        Item update = new Item("Green Apple", 1.09);
//        Mockito.when(itemRepository.findById(2L)).thenReturn(Optional.empty());
//        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(2L, update));
//    }
//
//    @Test
//    void testDeleteItem_found() {
//        Mockito.when(itemRepository.existsById(1L)).thenReturn(true);
//        Assertions.assertDoesNotThrow(() -> itemService.deleteItem(1L));
//        Mockito.verify(itemRepository).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteItem_notFound() {
//        Mockito.when(itemRepository.existsById(2L)).thenReturn(false);
//        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(2L));
//    }
}