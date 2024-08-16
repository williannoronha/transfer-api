package com.itau.transferapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.transferapi.model.entity.Transferencia;
import com.itau.transferapi.model.dto.TransferenciaDTO;
import com.itau.transferapi.service.TransferenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/transferencias")
public class TransferenciaController {
	
	@Autowired
    private TransferenciaService transferenciaService;

	@PostMapping
	@Operation(
	        summary = "Realiza uma transferência entre contas",
	        description = "Permite realizar uma transferência entre duas contas. Porém, o limite de transferência é de até R$10.000,00",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na solicitação"),
	            @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
	            @ApiResponse(responseCode = "500", description = "Limite Excedido")
	        }
	    )
    public ResponseEntity<Transferencia> createTransferencia(@Valid @RequestBody TransferenciaDTO transferenciaDTO) {
		Transferencia transferencia = transferenciaService.createTransferencia(transferenciaDTO);
        return new ResponseEntity<>(transferencia, HttpStatus.CREATED);
    }

    @GetMapping("/historico/{numeroConta}")
    @Operation(
	        summary = "Realiza a busca do histórico de transações.",
	        description = "Permite buscar o histórico das transferêncsias realizadas através do número da conta cadastrada.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "busca realizada com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na solicitação"),
	            @ApiResponse(responseCode = "404", description = "Conta não encontrada ou informações nulas")
	        }
	    )
    public ResponseEntity<List<Transferencia>> getTransferenciasByNumeroConta(@PathVariable String numeroConta) {
    	List<Transferencia> historico = transferenciaService.getTransferenciasByNumeroConta(numeroConta);
        return new ResponseEntity<>(historico, HttpStatus.OK);
    }

}
