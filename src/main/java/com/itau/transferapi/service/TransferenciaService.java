package com.itau.transferapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.transferapi.exception.InsufficientFundsException;
import com.itau.transferapi.exception.ResourceNotFoundException;
import com.itau.transferapi.model.Cliente;
import com.itau.transferapi.model.Transferencia;
import com.itau.transferapi.repository.IClienteRepository;
import com.itau.transferapi.repository.ITransferenciaRepository;


@Service
@Transactional
public class TransferenciaService {
	
	@Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ITransferenciaRepository transferenciaRepository;

    @Transactional
    public Transferencia realizarTransferencia(String contaOrigem, String contaDestino, BigDecimal valor) {
        Cliente clienteOrigem = clienteRepository.findByNumeroConta(contaOrigem)
                .orElseThrow(() -> new ResourceNotFoundException("Conta origem não encontrada"));
        Cliente clienteDestino = clienteRepository.findByNumeroConta(contaDestino)
                .orElseThrow(() -> new ResourceNotFoundException("Conta destino não encontrada"));

        if (clienteOrigem.getSaldo().compareTo(valor) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente");
        }

        clienteOrigem.setSaldo(clienteOrigem.getSaldo().subtract(valor));
        clienteDestino.setSaldo(clienteDestino.getSaldo().add(valor));

        Transferencia transferencia = new Transferencia(contaOrigem, contaDestino, valor, LocalDateTime.now(), true);
        transferenciaRepository.save(transferencia);

        clienteRepository.save(clienteOrigem);
        clienteRepository.save(clienteDestino);

        return transferencia;
    }

    public List<Transferencia> buscarHistoricoTransferencias(String numeroConta) {
        return transferenciaRepository.findByContaOrigemOrContaDestinoOrderByDataDesc(numeroConta, numeroConta);
    }

}
