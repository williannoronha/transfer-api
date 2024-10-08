package com.itau.transferapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.transferapi.model.entity.Cliente;
import com.itau.transferapi.model.dto.ClienteDTO;
import com.itau.transferapi.repository.IClienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private IClienteRepository clienteRepository;    
    
    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
    }
    

    @Test
    public void testCadastrarClienteSucesso() throws Exception {
    	mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"João\",\"numeroConta\":\"123456\",\"saldo\":1000}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nome").value("João"))
        .andExpect(jsonPath("$.numeroConta").value("123456"))
        .andExpect(jsonPath("$.saldo").value(1000));
    }

    @Test
    public void testListarClientesSucesso() throws Exception {
    	Cliente cliente1 = new Cliente("João", "123456", BigDecimal.valueOf(1000));
        Cliente cliente2 = new Cliente("Maria", "654321", BigDecimal.valueOf(2000));
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));
    }

    @Test
    public void testBuscarClientePorNumeroContaSucesso() throws Exception {
    	Cliente cliente = new Cliente("João", "123456", BigDecimal.valueOf(1000));
        clienteRepository.save(cliente);

        mockMvc.perform(get("/api/v1/clientes/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.numeroConta").value("123456"));
    }
    
    @Test
    public void testdReturnNotFoundWhenClienteDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/clientes/999999"))
                .andExpect(status().isNotFound());
    }

}
