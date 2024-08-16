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

import com.itau.transferapi.model.dto.ClienteDTO;
import com.itau.transferapi.model.entity.Cliente;
import com.itau.transferapi.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;


/**
 * A classe ClienteController
 * realiza a gestão de um novo Cliente
 * embora apenas seja cadastrado um cliente e informações da conta
 * e com isso temos apenas a visibilidade dessas informações, sem realizar outras persistências
 * aqui deixa claro o funcionamento do conjunto de peças como Spring, H2 e objetos nativos do Java
 * 
 * @author Willian Noronha
 * 
 */
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
	
	@Autowired
    private ClienteService clienteService;

    @PostMapping
    @Operation(
	        summary = "Cadastra um novo cliente.",
	        description = "Inclui um novo cliente para realizar operações ",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "cliente cadastrado com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na inclusão")
	        }
	    )
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.createCliente(clienteDTO));
    }

    @GetMapping
    @Operation(
	        summary = "Obtem todos os cliente.",
	        description = "Retorna uma lista de clientes cadastrdos",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "busca realizada com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na busca")
	        }
	    )
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAllClientes());
    }

    @GetMapping("/{numeroConta}")
    @Operation(
	        summary = "Obtem um cliente através da conta.",
	        description = "Retorna uma informações do cliente através do número da conta cadastrada.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "busca realizada com sucesso"),
	            @ApiResponse(responseCode = "400", description = "Erro na busca")
	        }
	    )
    public ResponseEntity<Cliente> getClienteByNumeroConta(@PathVariable String numeroConta) {
    	Cliente cliente = clienteService.getClienteByNumeroConta(numeroConta);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

}
