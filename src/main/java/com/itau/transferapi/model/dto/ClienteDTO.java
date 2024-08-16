package com.itau.transferapi.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class ClienteDTO {
	
	@NotNull
    private String nome;
	
	@NotNull
    private String numeroConta;
	
	@NotNull
    private BigDecimal saldo;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	
	

}
