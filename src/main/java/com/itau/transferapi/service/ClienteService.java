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
	
	public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorNumeroConta(String numeroConta) {
        return clienteRepository.findByNumeroConta(numeroConta)
                                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

}
