package com.ariellopes.clienteapi.rest.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;
import com.ariellopes.clienteapi.rest.controller.domain.request.NovoClienteDto;
import com.ariellopes.clienteapi.rest.controller.domain.response.ClienteDto;
import com.ariellopes.clienteapi.rest.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@ResponseStatus(HttpStatus.CREATED)
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<ClienteDto> create( @RequestBody @Validated NovoClienteDto dto) {
	
		ClienteEntity pacientesalvo = service.save(modelMapper.map(dto, ClienteEntity.class));
		
		URI uri = ServletUriComponentsBuilder.
				fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pacientesalvo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
