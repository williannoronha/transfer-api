package com.itau.transferapi.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	@NotNull
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 100)
    @Schema(description = "Nome do cliente", example = "1")
    private String nome;

	@NotNull
    @Column(unique = true, nullable = false)
    @Size(min = 10, max = 10)
    @Schema(description = "Número da conta do cliente", example = "1")
    private String numeroConta;

	@NotNull
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
