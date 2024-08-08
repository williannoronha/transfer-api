package com.itau.transferapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.transferapi.model.Transferencia;
import com.itau.transferapi.service.TransferenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/transferencias")
public class TransferenciaController {
	
	@Autowired
    private TransferenciaService transferenciaService;

	@PostMapping
	@Operation(
	        summary = "Realiza uma transferência entre contas",
	        description = "Permite realizar uma transferência entre duas contas.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na solicitação"),
	            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
	        }
	    )
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody Transferencia transferencia) {
        Transferencia novaTransferencia = transferenciaService.realizarTransferencia(
                transferencia.getContaOrigem(),
                transferencia.getContaDestino(),
                transferencia.getValor()
        );
        return ResponseEntity.ok(novaTransferencia);
    }

    @GetMapping("/{numeroConta}")
    @Operation(
	        summary = "Realiza a busca do histórico de transações.",
	        description = "Permite buscar o histórico das transferêncsias realizadas através do número da conta cadastrada.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "busca realizada com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na solicitação"),
	            @ApiResponse(responseCode = "404", description = "Conta não encontrada ou informações nulas")
	        }
	    )
    public ResponseEntity<List<Transferencia>> buscarHistoricoTransferencias(@PathVariable String numeroConta) {
        return ResponseEntity.ok(transferenciaService.buscarHistoricoTransferencias(numeroConta));
    }

}
