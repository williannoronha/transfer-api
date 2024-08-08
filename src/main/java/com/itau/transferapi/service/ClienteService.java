package com.itau.transferapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.transferapi.exception.ResourceNotFoundException;
import com.itau.transferapi.model.Cliente;
import com.itau.transferapi.repository.IClienteRepository;


@Service
@Transactional
public class ClienteService {
	
	@Autowired
    private IClienteRepository clienteRepository;
	
	public Cliente createCliente(Cliente cliente) {
		if (cliente.getNome() == null || cliente.getNumeroConta() == null || cliente.getSaldo() == null) {
            throw new IllegalArgumentException("Nome, número da conta e saldo são obrigatórios.");
        }

        if (clienteRepository.existsByNumeroConta(cliente.getNumeroConta())) {
            throw new IllegalArgumentException("Número da conta já existe.");
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteByNumeroConta(String numeroConta) {
        return clienteRepository.findByNumeroConta(numeroConta)
                                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

}
