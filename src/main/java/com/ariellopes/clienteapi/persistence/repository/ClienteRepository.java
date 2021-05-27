package com.ariellopes.clienteapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

	boolean existsByCpf(String cpf);

}
