package com.itau.transferapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.transferapi.exception.InsufficientFundsException;
import com.itau.transferapi.exception.ResourceNotFoundException;
import com.itau.transferapi.model.entity.*;
import com.itau.transferapi.model.dto.TransferenciaDTO;
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
    public Transferencia createTransferencia(TransferenciaDTO transferenciaDTO) {
    	

    	if (transferenciaDTO.valor() == null || transferenciaDTO.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        if (transferenciaDTO.valor().compareTo(new BigDecimal(10000)) > 0) {
            throw new IllegalArgumentException("Valor da transferência excede o limite de R$ 10.000,00.");
        }       	
        
        Transferencia transferencia = new Transferencia();

        try {
            // Verificações e execução da transferência
        	Cliente contaOrigem  = clienteRepository.findByNumeroConta(transferenciaDTO.contaOrigem())
                    .orElseThrow(() -> new ResourceNotFoundException("Conta origem não encontrada"));
            Cliente contaDestino = clienteRepository.findByNumeroConta(transferenciaDTO.contaDestino())

                    .orElseThrow(() -> new ResourceNotFoundException("Conta destino não encontrada"));
            
            if (contaOrigem == null || contaDestino == null) {
                throw new IllegalArgumentException("Conta origem ou destino não encontrada.");
            }            

            if (contaOrigem.getSaldo().compareTo(transferenciaDTO.valor()) < 0) {
                throw new InsufficientFundsException("Saldo insuficiente na conta de origem.");
            }

            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transferenciaDTO.valor()));
            contaDestino.setSaldo(contaDestino.getSaldo().add(transferenciaDTO.valor()));

            
            transferencia.setContaOrigem(transferenciaDTO.contaOrigem());
            transferencia.setContaDestino(transferenciaDTO.contaDestino());
            transferencia.setValor(transferenciaDTO.valor());

            transferencia.setData(LocalDateTime.now());
            transferencia.setSucesso(true);

            transferenciaRepository.save(transferencia);
            clienteRepository.save(contaOrigem);
            clienteRepository.save(contaDestino);

            return transferencia;
             	
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            // Marcar a transferência como falha e salvar no banco de dados
            transferencia.setSucesso(false);
            transferenciaRepository.save(transferencia);
            throw e;
        }        

    }

    public List<Transferencia> getTransferenciasByNumeroConta(String numeroConta) {
        return transferenciaRepository.findByContaOrigemOrContaDestinoOrderByDataDesc(numeroConta, numeroConta);
    }

}
