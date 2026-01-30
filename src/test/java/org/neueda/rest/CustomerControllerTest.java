package org.neueda.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.neueda.rest.entity.Customer;
import org.neueda.rest.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void testGetAllItems() throws Exception {
        mockMvc.perform(get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void testGetItemById() throws Exception {
        Customer customer = customerRepo.save(new Customer("Devid", "devid@gmail.com"));
        mockMvc.perform(get("/api/v1/customers/" + customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Devid"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer item = new Customer("Alexa", "alexa@microsoft.com");
        String json = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alexa"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerRepo.save(new Customer("Alexa", "alexa@gmail.com"));
        Customer update = new Customer("Alexander", "alexa@gmail.com");
        String json = objectMapper.writeValueAsString(update);
        mockMvc.perform(put("/api/v1/customers/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alexander"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = customerRepo.save(new Customer("TestUser", "test@gmail.com"));
        mockMvc.perform(delete("/api/v1/customers/" + customer.getId()))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(customerRepo.findById(customer.getId()).isPresent());
    }

    @Test
    void testGetCustomerById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/customers/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Not Found")));
    }

    @Test
    void testCreateCustomer_InvalidParams() throws Exception {
        Customer item = new Customer(null, null);
        String json = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("must be provided")));
    }
}