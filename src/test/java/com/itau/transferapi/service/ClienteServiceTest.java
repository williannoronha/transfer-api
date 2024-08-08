package com.itau.transferapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.itau.transferapi.model.Cliente;
import com.itau.transferapi.repository.*;

public class ClienteServiceTest {
	
	@InjectMocks
    private ClienteService clienteService;

    @Mock
    private IClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCliente() {
        Cliente cliente = new Cliente("Bernadete", "123456", new BigDecimal("1000.00"));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente createdCliente = clienteService.createCliente(cliente);

        assertEquals("Bernadete", createdCliente.getNome());
        assertEquals("123456", createdCliente.getNumeroConta());
        assertEquals(new BigDecimal("1000.00"), createdCliente.getSaldo());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testGetAllClientes() {
        List<Cliente> clientes = Arrays.asList(
                new Cliente("Bernadete", "123456", new BigDecimal("1000.00")),
                new Cliente("Maria", "654321", new BigDecimal("2000.00"))
        );
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAllClientes();

        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testGetClienteByNumeroConta() {
        Cliente cliente = new Cliente("Bernadete", "123456", new BigDecimal("1000.00"));
        when(clienteRepository.findByNumeroConta("123456")).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteByNumeroConta("123456");

        assertNotNull(result);
        assertEquals("Bernadete", result.getNome());
        assertEquals("123456", result.getNumeroConta());
        verify(clienteRepository, times(1)).findByNumeroConta("123456");
    }    

}
