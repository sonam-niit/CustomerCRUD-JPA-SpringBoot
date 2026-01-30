package org.neueda.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    void testCreateItem() throws Exception {
        Customer item = new Customer("Alexa", "alexa@microsoft.com");
        String json = objectMapper.writeValueAsString(item);
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CreatedItem"));
    }
//
//    @Test
//    void testUpdateItem() throws Exception {
//        Item item = itemRepository.save(new Item("OldName", 1.99));
//        Item update = new Item("UpdatedName", 2.99);
//        String json = objectMapper.writeValueAsString(update);
//        mockMvc.perform(put("/items/" + item.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("UpdatedName"));
//    }
//
//    @Test
//    void testDeleteItem() throws Exception {
//        Item item = itemRepository.save(new Item("ToDelete", 0.99));
//        mockMvc.perform(delete("/items/" + item.getId()))
//                .andExpect(status().isNoContent());
//        Assertions.assertFalse(itemRepository.findById(item.getId()).isPresent());
//    }
//
//    @Test
//    void testGetItemById_NotFound() throws Exception {
//        mockMvc.perform(get("/items/99999"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(org.hamcrest.Matchers.containsString("not found")));
//    }
//
//    @Test
//    void testCreateItem_InvalidParams() throws Exception {
//        Item item = new Item(null, 0);
//        String json = objectMapper.writeValueAsString(item);
//        mockMvc.perform(post("/items")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string(org.hamcrest.Matchers.containsString("must be provided")));
//    }
}