package com.itau.transferapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.transferapi.model.Cliente;
import com.itau.transferapi.model.Transferencia;
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
    private ObjectMapper objectMapper;
    

    @Test
    public void testRealizarTransferenciaSucesso() throws Exception {
        // Primeiro passo, criando massa de clientes
        Cliente clienteOrigem = new Cliente("Maria das Dores", "17583-5", BigDecimal.valueOf(1000.00));
        Cliente clienteDestino = new Cliente("Joaquina Maria", "67890", BigDecimal.valueOf(500.00));

        // segundo passo, salvando clientes no banco de dados
        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteOrigem)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDestino)))
                .andExpect(status().isCreated());

        // terceiro passo, realizar a transferência
        Transferencia transferencia = new Transferencia("17583-5", "67890", BigDecimal.valueOf(100.00), null, null);
        mockMvc.perform(post("/api/v1/transferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferencia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contaOrigem").value("17583-5"))
                .andExpect(jsonPath("$.contaDestino").value("67890"))
                .andExpect(jsonPath("$.valor").value(100.00))
                .andExpect(jsonPath("$.sucesso").value(true));
    }

    @Test
    public void testBuscarHistoricoTransferenciasSucesso() throws Exception {
        mockMvc.perform(get("/api/v1/transferencias/17583-5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
