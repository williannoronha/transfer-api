package com.itau.transferapi.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@Schema(description = "Representa uma cliente cadastrado")
public class Cliente {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID da cliente", example = "1")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Nome do cliente", example = "1")
    private String nome;

    @Column(unique = true, nullable = false)
    @Schema(description = "Número da conta do cliente", example = "1")
    private String numeroConta;

    @Schema(description = "Saldo do cliente", example = "1")
    private BigDecimal saldo;
    
    // Construtor 
    public Cliente() {
    	
    }
    
    public Cliente(String nome, String numeroConta, BigDecimal saldo) {
    	this.nome = nome;
    	this.numeroConta = numeroConta;
    	this.saldo = saldo;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
