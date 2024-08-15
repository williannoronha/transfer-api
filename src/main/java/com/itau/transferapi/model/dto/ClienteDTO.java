package com.itau.transferapi.model.dto;

import java.math.BigDecimal;

public class ClienteDTO {
	
	private Long id;
    private String nome;
    private String numeroConta;
    private BigDecimal saldo;
    
    
	public ClienteDTO(String nome, String numeroConta, BigDecimal saldo) {
		super();
		this.nome = nome;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
	}
	
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
