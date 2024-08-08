package com.itau.transferapi.service;

import java.math.BigDecimal;
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
    public Transferencia createTransferencia(Transferencia transferencia) {
    	
    	if (transferencia.getValor() == null || transferencia.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        if (transferencia.getValor().compareTo(new BigDecimal(10000)) > 0) {
            throw new IllegalArgumentException("Valor da transferência excede o limite de R$ 10.000,00.");
        }       	
        

        try {
            // Verificações e execução da transferência
        	Cliente contaOrigem  = clienteRepository.findByNumeroConta(transferencia.getContaOrigem())
                    .orElseThrow(() -> new ResourceNotFoundException("Conta origem não encontrada"));
            Cliente contaDestino = clienteRepository.findByNumeroConta(transferencia.getContaDestino())
                    .orElseThrow(() -> new ResourceNotFoundException("Conta destino não encontrada"));
            
            if (contaOrigem == null || contaDestino == null) {
                throw new IllegalArgumentException("Conta origem ou destino não encontrada.");
            }
            
            if (contaOrigem.getSaldo().compareTo(transferencia.getValor()) < 0) {
                throw new InsufficientFundsException("Saldo insuficiente na conta de origem.");
            }

            if (contaOrigem.getSaldo().compareTo(transferencia.getValor()) < 0) {
                transferencia.setSucesso(false);
            } else {
                contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transferencia.getValor()));
                contaDestino.setSaldo(contaDestino.getSaldo().add(transferencia.getValor()));
                clienteRepository.save(contaOrigem);
                clienteRepository.save(contaDestino);
                transferencia.setSucesso(true);
            }
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            // Marcar a transferência como falha e salvar no banco de dados
            transferencia.setSucesso(false);
            transferenciaRepository.save(transferencia);
            throw e;
        }
        
        transferencia.setSucesso(true);
        return transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> getTransferenciasByNumeroConta(String numeroConta) {
        return transferenciaRepository.findByContaOrigemOrContaDestinoOrderByDataDesc(numeroConta, numeroConta);
    }

}
