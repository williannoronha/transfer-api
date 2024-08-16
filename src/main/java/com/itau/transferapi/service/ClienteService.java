package com.itau.transferapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itau.transferapi.exception.ResourceNotFoundException;
import com.itau.transferapi.model.entity.Cliente;
import com.itau.transferapi.model.dto.ClienteDTO;
import com.itau.transferapi.repository.IClienteRepository;


@Service
@Transactional
public class ClienteService {
	
	@Autowired
    private IClienteRepository clienteRepository;
	
	public Cliente createCliente(ClienteDTO clienteDTO) {
		if (clienteDTO.nome() == null || clienteDTO.numeroConta() == null || clienteDTO.saldo() == null) {
            throw new IllegalArgumentException("Nome, número da conta e saldo são obrigatórios.");
        }

        if (clienteRepository.existsByNumeroConta(clienteDTO.numeroConta())) {

            throw new IllegalArgumentException("Número da conta já existe.");
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setNumeroConta(clienteDTO.numeroConta());
        cliente.setSaldo(clienteDTO.saldo());


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
