package com.itau.transferapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.itau.transferapi.exception.ResourceNotFoundException;
import com.itau.transferapi.model.Cliente;
import com.itau.transferapi.repository.*;
import com.itau.transferapi.service.*;

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
    public void testSalvarClienteSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria das Dores");
        cliente.setNumeroConta("17583-5");
        cliente.setSaldo(BigDecimal.valueOf(1000.00));

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.cadastrarCliente(cliente);
        assertNotNull(result);
        assertEquals("Maria das Dores", result.getNome());
    }

    @Test
    public void testBuscarClientePorNumeroContaSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria das Dores");
        cliente.setNumeroConta("17583-5");
        cliente.setSaldo(BigDecimal.valueOf(1000.00));

        when(clienteRepository.findByNumeroConta("17583-5")).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.buscarPorNumeroConta("17583-5");
        assertNotNull(result);
        assertEquals("Maria das Dores", result.getNome());
    }

    @Test
    public void testBuscarClientePorNumeroContaNaoEncontrado() {
        when(clienteRepository.findByNumeroConta("17583-5")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.buscarPorNumeroConta("17583-5");
        });
    }

}
