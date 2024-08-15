package com.itau.transferapi.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferenciaDTO {
	
	
    private Long id;	
    private String contaOrigem;	
    private String contaDestino;	
    private BigDecimal valor;	
    private LocalDateTime data;	
    private Boolean sucesso;
    
    
	public TransferenciaDTO(String contaOrigem, String contaDestino, BigDecimal valor, LocalDateTime data,
			Boolean sucesso) {
		super();
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
