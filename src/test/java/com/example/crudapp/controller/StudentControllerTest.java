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
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateStudent() throws Exception {
        String json = "{" +
                "\"firstName\": \"Test\"," +
                "\"lastName\": \"Student\"," +
                "\"email\": \"student@example.com\"," +
                "\"phone\": \"1234567890\"," +
                "\"enrollmentNumber\": \"EN123\"," +
                "\"address\": {" +
                "\"street\": \"Test St\"," +
                "\"city\": \"Test City\"," +
                "\"state\": \"TS\"," +
                "\"postalCode\": \"00000\"," +
                "\"country\": \"Testland\"}}";
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
