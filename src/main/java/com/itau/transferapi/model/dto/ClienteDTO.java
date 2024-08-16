package com.itau.transferapi.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record ClienteDTO (String nome, String numeroConta, BigDecimal saldo) {

}
