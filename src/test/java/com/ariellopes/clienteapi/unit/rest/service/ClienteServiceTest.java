package com.ariellopes.clienteapi.unit.rest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.print.attribute.standard.Severity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ariellopes.clienteapi.exception.model.CpfJaCadastradoException;
import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;
import com.ariellopes.clienteapi.persistence.entity.EnderecoEntity;
import com.ariellopes.clienteapi.persistence.repository.ClienteRepository;
import com.ariellopes.clienteapi.rest.controller.domain.EstadoEnum;
import com.ariellopes.clienteapi.rest.service.ClienteService;
import com.ariellopes.clienteapi.rest.service.impl.ClienteServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

	ClienteService service;
	
	@MockBean
	ClienteRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new ClienteServiceImpl(repository);
		
	}
	
	@Test
	@DisplayName("Deve salvar um cliente")
	public void saveClientTest() {
		
		//Cenario
		
		Mockito.when( repository.existsByCpf(Mockito.anyString()))
		.thenReturn(false);
		
		ClienteEntity clienteEntity = ClienteEntity.builder()
				.nome("Ariel")
				.cpf("000.000.000-00")
				.email("Ariel-edit@hotmail.com")
				.endereco( EnderecoEntity.builder()
							.cep("7901255")
							.rua("Mundo da Lua")
							.bairro("Santa fé")
							.numero("487")
							.estado(EstadoEnum.MATO_GROSSO_DO_SUL)
							.build())
				.build();
		
		
		Mockito.when( repository.save(clienteEntity) ).thenReturn(ClienteEntity.builder()
														.id(14l)
														.nome("Ariel")
														.cpf("000.000.000-00")
														.email("Ariel-edit@hotmail.com")
														.endereco( EnderecoEntity.builder()
																	.id(1l)
																	.cep("7901255")
																	.rua("Mundo da Lua")
																	.bairro("Santa fé")
																	.numero("487")
																	.estado(EstadoEnum.MATO_GROSSO_DO_SUL)
																	.build())
														.build());
		
		//Execução
		ClienteEntity clienteSalvo = service.save(clienteEntity);
		
		//Verrificação
		assertThat( clienteSalvo.getId() ).isNotNull();
		assertThat( clienteSalvo.getNome()).isEqualTo("Ariel");
		assertThat( clienteSalvo.getCpf()).isEqualTo("000.000.000-00");
		assertThat( clienteSalvo.getEmail()).isEqualTo("Ariel-edit@hotmail.com");
		assertThat( clienteSalvo.getEndereco().getId()).isEqualTo(1l);
		assertThat( clienteSalvo.getEndereco().getCep()).isEqualTo("7901255");
		assertThat( clienteSalvo.getEndereco().getRua()).isEqualTo("Mundo da Lua");
		assertThat( clienteSalvo.getEndereco().getBairro()).isEqualTo("Santa fé");
		assertThat( clienteSalvo.getEndereco().getNumero()).isEqualTo("487");
		assertThat( clienteSalvo.getEndereco().getEstado()).isEqualTo(EstadoEnum.MATO_GROSSO_DO_SUL);
		
	}
	
	@Test
	public void findById() {
		
		//Cenario
		
		
		//Execução
		
		
		Mockito.when( repository.findById(1l) ).thenReturn(Optional.of(ClienteEntity.builder()
														.id(14l)
														.nome("Ariel")
														.cpf("000.000.000-00")
														.email("Ariel-edit@hotmail.com")
														.endereco( EnderecoEntity.builder()
																	.id(1l)
																	.cep("7901255")
																	.rua("Mundo da Lua")
																	.bairro("Santa fé")
																	.numero("487")
																	.estado(EstadoEnum.MATO_GROSSO_DO_SUL)
																	.build())
														.build()));
		
		ClienteEntity clienteSalvo = service.findById(1l);
		
		//Verrificação
		assertThat( clienteSalvo.getCpf()).isNotNull();
	}
	
	@Test
	@DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
	public void deve_lancar_exercao_ao_tentar_salvar_livro_duplicado() {
		//Cenario
		
		ClienteEntity cliente = ClienteEntity.builder()
				.id(14l)
				.nome("Ariel")
				.cpf("000.000.000-00")
				.email("Ariel-edit@hotmail.com")
				.endereco( EnderecoEntity.builder()
							.id(1l)
							.cep("7901255")
							.rua("Mundo da Lua")
							.bairro("Santa fé")
							.numero("487")
							.estado(EstadoEnum.MATO_GROSSO_DO_SUL)
							.build())
				.build();
		
		
		Mockito.when( repository.existsByCpf(Mockito.anyString()))
												.thenReturn(true);
		
		//Exercucão
		
		Throwable exeption = Assertions.catchThrowable(() -> service.save(cliente));
		
		//Verrificações
		assertThat(exeption)
				.isInstanceOf(CpfJaCadastradoException.class)
				.hasMessage("Cpf já cadastrado");
		
		//Verrificar que ele NUNCA VAi chamar o SAVE
		Mockito.verify(repository,Mockito.never()).save(cliente);
	}
	
	

	
	
	
	
	
	
	
	
}
