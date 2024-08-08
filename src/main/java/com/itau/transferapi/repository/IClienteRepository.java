package com.itau.transferapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itau.transferapi.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long>{
	
	Optional<Cliente> findByNumeroConta(String numeroConta);

}
