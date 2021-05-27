package com.ariellopes.clienteapi.rest.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ariellopes.clienteapi.exception.model.CpfJaCadastradoException;
import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;
import com.ariellopes.clienteapi.persistence.repository.ClienteRepository;
import com.ariellopes.clienteapi.rest.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteRepository repository;
	
	public ClienteServiceImpl(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ClienteEntity save(ClienteEntity cliente) {
		
		verrificarCpfJaExistente(cliente.getCpf());
		return repository.save(cliente);
	}

	@Override
	public ClienteEntity findById(long buscarPorId) {
		 Optional<ClienteEntity> clienteEntity = repository.findById(buscarPorId);
		 clienteEntity.orElseThrow(()  -> new RuntimeException("Clienta não encontado") );
		 
		 return clienteEntity.get();
	}
	
	private void verrificarCpfJaExistente(String cpf) {
		 
		  if(repository.existsByCpf(cpf)) {
			  throw new CpfJaCadastradoException("Cpf já cadastrado");
		  }
		
	}

}
