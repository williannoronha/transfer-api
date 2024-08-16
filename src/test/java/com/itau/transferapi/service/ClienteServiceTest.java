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

import com.itau.transferapi.exception.ResourceNotFoundException;
import com.itau.transferapi.model.dto.ClienteDTO;
import com.itau.transferapi.model.entity.Cliente;
import com.itau.transferapi.repository.*;

/**
 * O objetivo da classe ClienteServiceTest
 * é realizar a testes unitários da gestão de um novo Cliente
 * embora apenas seja cadastrado um cliente e informações da conta
 * 
 * @author Willian Noronha
 * 
 */
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
    	ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João");
        clienteDTO.setNumeroConta("123456");
        clienteDTO.setSaldo(BigDecimal.valueOf(1000));

        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());

        Cliente cliente = clienteService.createCliente(clienteDTO);

        assertNotNull(cliente);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
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
    	String numeroConta = "123456";
        Cliente cliente = new Cliente();
        cliente.setNumeroConta(numeroConta);

        when(clienteRepository.findByNumeroConta(numeroConta)).thenReturn(Optional.of(cliente));

        Cliente foundCliente = clienteService.getClienteByNumeroConta(numeroConta);

        assertNotNull(foundCliente);
        assertEquals(numeroConta, foundCliente.getNumeroConta());
    }  
    
    @Test
    void testThrowResourceNotFoundExceptionWhenClienteNotFound() {
        String numeroConta = "123456";

        when(clienteRepository.findByNumeroConta(numeroConta)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.getClienteByNumeroConta(numeroConta));
    }

}
