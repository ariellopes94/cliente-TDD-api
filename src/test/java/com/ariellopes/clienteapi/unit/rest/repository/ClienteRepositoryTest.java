package com.ariellopes.clienteapi.unit.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;
import com.ariellopes.clienteapi.persistence.repository.ClienteRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ClienteRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	ClienteRepository repository;
	
	@Test
	@DisplayName("deve retornar verdadeiro quando existir um cliente na base com o cpf informado")
	public void deve_retornar_verdadeiro_quando_existir_cliente_com_o_cpf() {
		//Cenario
		String cpf = "000.000.000-45";
		
		ClienteEntity cliente = ClienteEntity.builder() //Simular o cliente
									.cpf(cpf)
									.nome("Ariel Lopes")
									.email("ariel-edit@hotmail.com")
								.build();
									
		
		entityManager.persist(cliente); // Salvar no banco
		
		//Execução
		boolean exists = repository.existsByCpf(cpf); //Buscar do BAnco
	
		//verrificação
		
		assertThat(exists).isTrue();
	}
	
	
	@Test
	@DisplayName("deve retornar falso quando não existir um cliente na base com o cpf informado")
	public void deve_retornar_falso_quando_nao_existir_cliente_com_o_cpf() {
		//Cenario
		String cpf = "000.000.000-45";
		
		//Execução
		boolean exists = repository.existsByCpf(cpf); //Buscar do BAnco
	
		//verrificação
		assertThat(exists).isFalse();
	}
	
	
	
	
	
	
	
	

}
