package com.example.crudapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllAddresses() throws Exception {
        mockMvc.perform(get("/api/addresses"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateAddress() throws Exception {
        String json = "{" +
                "\"street\": \"Test St\"," +
                "\"city\": \"Test City\"," +
                "\"state\": \"TS\"," +
                "\"postalCode\": \"00000\"," +
                "\"country\": \"Testland\"}";
        mockMvc.perform(post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
