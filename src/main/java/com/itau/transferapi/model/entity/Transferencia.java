package com.itau.transferapi.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@Schema(description = "Representa uma transferência entre contas")
public class Transferencia {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID da transferência", example = "1")
    private Long id;

	@NotNull
	@Schema(description = "Número da conta de origem", example = "12345")
    private String contaOrigem;
	
	@NotNull
	@Schema(description = "Número da conta de destino", example = "67890")
    private String contaDestino;
	
	@NotNull
	@Schema(description = "Valor da transferência", example = "100.0")
    private BigDecimal valor;
	
	@Schema(description = "Data da transferência", example = "2024-08-07T20:00:00")
    private LocalDateTime data;
	
	@Schema(description = "Indica se a transferência foi bem-sucedida", example = "true")
    private Boolean sucesso;
    
    // Construtor padrão
    public Transferencia() {
    }
    
    public Transferencia(String contaOrigem, String contaDestino, BigDecimal valor, LocalDateTime data, Boolean sucesso) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.data = data;
        this.sucesso = sucesso;
    }

	public String getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public String getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
    
    

}
