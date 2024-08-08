package com.itau.transferapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itau.transferapi.model.Transferencia;

public interface ITransferenciaRepository extends JpaRepository<Transferencia, Long>{
	
	List<Transferencia> findByContaOrigemOrContaDestinoOrderByDataDesc(String contaOrigem, String contaDestino);

}
