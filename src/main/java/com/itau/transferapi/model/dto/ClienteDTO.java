package com.itau.transferapi.model.dto;

import java.math.BigDecimal;


public record ClienteDTO (String nome, String numeroConta, BigDecimal saldo) {


}
