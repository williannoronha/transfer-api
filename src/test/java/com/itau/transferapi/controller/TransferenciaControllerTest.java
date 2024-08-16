package com.itau.transferapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.transferapi.model.entity.*;
import com.itau.transferapi.repository.*;

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
public class TransferenciaControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ITransferenciaRepository transferenciaRepository;

    @BeforeEach
    public void setup() {
        transferenciaRepository.deleteAll();
        clienteRepository.deleteAll();
    }
    

    @Test
    public void testRealizarTransferenciaSucesso() throws Exception {
    	Cliente contaOrigem = new Cliente("João", "123456", BigDecimal.valueOf(1000));
        Cliente contaDestino = new Cliente("Maria", "654321", BigDecimal.valueOf(500));
        clienteRepository.save(contaOrigem);
        clienteRepository.save(contaDestino);

        mockMvc.perform(post("/api/v1/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contaOrigem\":\"123456\",\"contaDestino\":\"654321\",\"valor\":200}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.valor").value(200))
                .andExpect(jsonPath("$.contaOrigem.numeroConta").value("123456"))
                .andExpect(jsonPath("$.contaDestino.numeroConta").value("654321"));
    }

    @Test
    public void testdReturnInsufficientFundsException() throws Exception {
        Cliente contaOrigem = new Cliente("João", "123456", BigDecimal.valueOf(100));
        Cliente contaDestino = new Cliente("Maria", "654321", BigDecimal.valueOf(500));
        clienteRepository.save(contaOrigem);
        clienteRepository.save(contaDestino);

        mockMvc.perform(post("/api/v1/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contaOrigem\":\"123456\",\"contaDestino\":\"654321\",\"valor\":200}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Saldo insuficiente na conta de origem"));
    }

    @Test
    public void testdReturnTransferenciasByNumeroConta() throws Exception {
        Cliente contaOrigem = new Cliente("João", "123456", BigDecimal.valueOf(1000));
        Cliente contaDestino = new Cliente("Maria", "654321", BigDecimal.valueOf(500));
        clienteRepository.save(contaOrigem);
        clienteRepository.save(contaDestino);

        mockMvc.perform(post("/api/v1/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contaOrigem\":\"123456\",\"contaDestino\":\"654321\",\"valor\":200}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/transferencias/historico/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].valor").value(200))
                .andExpect(jsonPath("$[0].contaOrigem.numeroConta").value("123456"));
    }

}
