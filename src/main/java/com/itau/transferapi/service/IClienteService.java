package com.itau.transferapi.service;

import java.util.List;

import com.itau.transferapi.model.dto.ClienteDTO;

public interface IClienteService {
	
	ClienteDTO createCliente(ClienteDTO clienteDTO);
    List<ClienteDTO> getClientes();
    ClienteDTO findByNumeroConta(String numeroConta);

}
