package com.itau.transferapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.itau.transferapi.exception.InsufficientFundsException;
import com.itau.transferapi.model.dto.TransferenciaDTO;
import com.itau.transferapi.model.entity.*;
import com.itau.transferapi.repository.*;
import com.itau.transferapi.service.*;

/**
 * O objetivo da classe TransferenciaServiceTest
 * é realizar a testes unitários da gestão de uma nova transferência
 * embora apenas seja cadastrado um cliente e informações da conta
 * 
 * @author Willian Noronha
 * 
 */
public class TransferenciaServiceTest {
	
	@InjectMocks
    private TransferenciaService transferenciaService;

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private ITransferenciaRepository transferenciaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransferencia() {
    	TransferenciaDTO transferenciaDTO = new TransferenciaDTO(null, "12345", "67890", new BigDecimal("500.00"));

        Transferencia createdTransferencia = transferenciaService.createTransferencia(transferenciaDTO);

        assertNotNull(createdTransferencia.getId());
        assertEquals("12345", createdTransferencia.getContaOrigem());
    }

    

    @Test
    void testGetTransferenciasByNumeroConta() {
        List<Transferencia> transferencias = Arrays.asList(
                new Transferencia("123456", "654321", new BigDecimal("200.00"), null, true),
                new Transferencia("123456", "987654", new BigDecimal("100.00"), null, true)
        );
        when(transferenciaRepository.findByContaOrigemOrContaDestinoOrderByDataDesc("123456", "123456")).thenReturn(transferencias);

        List<Transferencia> result = transferenciaService.getTransferenciasByNumeroConta("123456");

        assertEquals(2, result.size());
        verify(transferenciaRepository, times(1)).findByContaOrigemOrContaDestinoOrderByDataDesc("123456", "123456");
    }
    
    @Test
    void testTransferenciaValorZero() {
    	TransferenciaDTO transferenciaDTO = new TransferenciaDTO(null, "12345", "67890", new BigDecimal("0"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.createTransferencia(transferenciaDTO);
        });

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testTransferenciaValorNulo() {
    	TransferenciaDTO transferenciaDTO = new TransferenciaDTO(null, "12345", "67890", null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.createTransferencia(transferenciaDTO);
        });

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }

}
