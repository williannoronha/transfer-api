package com.itau.transferapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public void testRealizarTransferenciaSucesso() {
        Cliente origem = new Cliente();
        origem.setNumeroConta("17583-5");
        origem.setSaldo(BigDecimal.valueOf(5000.00));

        Cliente destino = new Cliente();
        destino.setNumeroConta("67890");
        destino.setSaldo(BigDecimal.valueOf(1000.00));

        when(clienteRepository.findByNumeroConta("17583-5")).thenReturn(Optional.of(origem));
        when(clienteRepository.findByNumeroConta("67890")).thenReturn(Optional.of(destino));
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(new Transferencia("17583-5", "67890", BigDecimal.valueOf(100.00), LocalDateTime.now(), true));

        Transferencia result = transferenciaService.realizarTransferencia("17583-5", "67890", BigDecimal.valueOf(100.00));
        assertNotNull(result);
        assertTrue(result.getSucesso());
        verify(clienteRepository, times(1)).save(origem);
        verify(clienteRepository, times(1)).save(destino);
    }

    @Test
    public void testRealizarTransferenciaSaldoInsuficiente() {
        Cliente origem = new Cliente();
        origem.setNumeroConta("17583-5");
        origem.setSaldo(BigDecimal.valueOf(50.00));

        Cliente destino = new Cliente();
        destino.setNumeroConta("67890");
        destino.setSaldo(BigDecimal.valueOf(1000.00));

        when(clienteRepository.findByNumeroConta("17583-5")).thenReturn(Optional.of(origem));
        when(clienteRepository.findByNumeroConta("67890")).thenReturn(Optional.of(destino));

        assertThrows(InsufficientFundsException.class, () -> {
            transferenciaService.realizarTransferencia("17583-5", "67890", BigDecimal.valueOf(100.00));
        });
    }

    @Test
    public void testBuscarHistoricoTransferenciasSucesso() {
        Transferencia transferencia = new Transferencia("17583-5", "67890", BigDecimal.valueOf(100.00), LocalDateTime.now(), true);
        when(transferenciaRepository.findByContaOrigemOrContaDestinoOrderByDataDesc("17583-5", "17583-5")).thenReturn(Collections.singletonList(transferencia));

        List<Transferencia> result = transferenciaService.buscarHistoricoTransferencias("17583-5");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

}
