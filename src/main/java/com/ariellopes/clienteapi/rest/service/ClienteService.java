package com.ariellopes.clienteapi.rest.service;

import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;

public interface ClienteService {

	ClienteEntity save(ClienteEntity cliente);

	ClienteEntity findById(long buscarPorId);
}
