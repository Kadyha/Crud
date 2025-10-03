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
class ProfessorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllProfessors() throws Exception {
        mockMvc.perform(get("/api/professors"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateProfessor() throws Exception {
        String json = "{" +
                "\"firstName\": \"Test\"," +
                "\"lastName\": \"Professor\"," +
                "\"email\": \"professor@example.com\"," +
                "\"phone\": \"1234567890\"," +
                "\"department\": \"Math\"," +
                "\"address\": {" +
                "\"street\": \"Test St\"," +
                "\"city\": \"Test City\"," +
                "\"state\": \"TS\"," +
                "\"postalCode\": \"00000\"," +
                "\"country\": \"Testland\"}}";
        mockMvc.perform(post("/api/professors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
