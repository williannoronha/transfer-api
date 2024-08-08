package com.itau.transferapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.transferapi.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    

    @Test
    public void testCadastrarClienteSucesso() throws Exception {
        Cliente cliente = new Cliente("Maria das Dores", "17583-5", BigDecimal.valueOf(1000.00));
        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Maria das Dores"))
                .andExpect(jsonPath("$.numeroConta").value("17583-5"))
                .andExpect(jsonPath("$.saldo").value(1000.00));
    }

    @Test
    public void testListarClientesSucesso() throws Exception {
        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testBuscarClientePorNumeroContaSucesso() throws Exception {
        mockMvc.perform(get("/api/v1/clientes/17583-5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConta").value("17583-5"));
    }

}
