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
    	TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        transferenciaDTO.setContaOrigem("123456");
        transferenciaDTO.setContaDestino("654321");
        transferenciaDTO.setValor(BigDecimal.valueOf(500));

        Cliente contaOrigem = new Cliente();
        contaOrigem.setNumeroConta("123456");
        contaOrigem.setSaldo(BigDecimal.valueOf(1000));

        Cliente contaDestino = new Cliente();
        contaDestino.setNumeroConta("654321");
        contaDestino.setSaldo(BigDecimal.valueOf(2000));

        when(clienteRepository.findByNumeroConta("123456")).thenReturn(Optional.of(contaOrigem));
        when(clienteRepository.findByNumeroConta("654321")).thenReturn(Optional.of(contaDestino));

        Transferencia transferencia = transferenciaService.createTransferencia(transferenciaDTO);

        assertNotNull(transferencia);
        assertTrue(transferencia.getSucesso());
        verify(transferenciaRepository, times(1)).save(any(Transferencia.class));
        verify(clienteRepository, times(1)).save(contaOrigem);
        verify(clienteRepository, times(1)).save(contaDestino);
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
    	TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
    	transferenciaDTO.setContaOrigem("12345");
    	transferenciaDTO.setContaDestino("67890");
    	transferenciaDTO.setValor(BigDecimal.ZERO);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.createTransferencia(transferenciaDTO);
        });

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testTransferenciaValorNulo() {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        transferenciaDTO.setContaOrigem("12345");
        transferenciaDTO.setContaDestino("67890");
        transferenciaDTO.setValor(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.createTransferencia(transferenciaDTO);
        });

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }

}
