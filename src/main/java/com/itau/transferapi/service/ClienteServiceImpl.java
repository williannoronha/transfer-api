package com.itau.transferapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itau.transferapi.model.dto.ClienteDTO;
import com.itau.transferapi.repository.IClienteRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
    private IClienteRepository clienteRepository;
	
	@Override
	public ClienteDTO createCliente(ClienteDTO clienteDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteDTO> getClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteDTO findByNumeroConta(String numeroConta) {
		// TODO Auto-generated method stub
		return null;
	}

}
