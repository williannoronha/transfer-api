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
import com.itau.transferapi.model.*;
import com.itau.transferapi.repository.*;
import com.itau.transferapi.service.*;

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
        Cliente contaOrigem = new Cliente("Bernadete", "123456", new BigDecimal("1000.00"));
        Cliente contaDestino = new Cliente("Maria", "654321", new BigDecimal("500.00"));
        Transferencia transferencia = new Transferencia("123456", "654321", new BigDecimal("200.00"), null, true);

        when(clienteRepository.findByNumeroConta("123456")).thenReturn(Optional.of(contaOrigem));
        when(clienteRepository.findByNumeroConta("654321")).thenReturn(Optional.of(contaDestino));
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        Transferencia result = transferenciaService.createTransferencia(transferencia);

        assertTrue(result.getSucesso());
        assertEquals(new BigDecimal("800.00"), contaOrigem.getSaldo());
        assertEquals(new BigDecimal("700.00"), contaDestino.getSaldo());
        verify(clienteRepository, times(1)).save(contaOrigem);
        verify(clienteRepository, times(1)).save(contaDestino);
        verify(transferenciaRepository, times(1)).save(transferencia);
    }

    @Test
    void testCreateTransferenciaInsufficientFunds() {
        Cliente contaOrigem = new Cliente("Bernadete", "123456", new BigDecimal("100.00"));
        Cliente contaDestino = new Cliente("Maria", "654321", new BigDecimal("500.00"));
        Transferencia transferencia = new Transferencia("123456", "654321", new BigDecimal("200.00"), null, false);

        when(clienteRepository.findByNumeroConta("123456")).thenReturn(Optional.of(contaOrigem));
        when(clienteRepository.findByNumeroConta("654321")).thenReturn(Optional.of(contaDestino));
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        Transferencia result = transferenciaService.createTransferencia(transferencia);

        assertFalse(result.getSucesso());
        assertEquals(new BigDecimal("100.00"), contaOrigem.getSaldo());
        assertEquals(new BigDecimal("500.00"), contaDestino.getSaldo());
        verify(clienteRepository, times(0)).save(contaOrigem);
        verify(clienteRepository, times(0)).save(contaDestino);
        verify(transferenciaRepository, times(1)).save(transferencia);
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

}
