package com.itau.transferapi.model.dto;

import java.math.BigDecimal;


public record TransferenciaDTO (Long id, String contaOrigem, String contaDestino, BigDecimal valor) {

}
