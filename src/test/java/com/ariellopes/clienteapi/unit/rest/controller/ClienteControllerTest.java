package com.ariellopes.clienteapi.unit.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ariellopes.clienteapi.exception.model.CpfJaCadastradoException;
import com.ariellopes.clienteapi.exception.model.IdJaCadastradoException;
import com.ariellopes.clienteapi.persistence.entity.ClienteEntity;
import com.ariellopes.clienteapi.persistence.entity.EnderecoEntity;
import com.ariellopes.clienteapi.rest.controller.domain.EstadoEnum;
import com.ariellopes.clienteapi.rest.controller.domain.request.NovoClienteDto;
import com.ariellopes.clienteapi.rest.controller.domain.response.ClienteDto;
import com.ariellopes.clienteapi.rest.controller.domain.response.EnderecoDto;
import com.ariellopes.clienteapi.rest.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

	static String CLIENTE_API = "/api/clientes";

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClienteService service;

	@Test
	@DisplayName("Deve criar um cliente com sucesso")
	public void createClientTest() throws Exception {

		NovoClienteDto novoClienteDto = NovoClienteDto.builder().nome("ariel").cpf("000.000.000-00")
				.email("Ariel-edit@hotmail.com")
				.endereco(EnderecoDto.builder().id(1l).cep("7901255").rua("Mundo da Lua").bairro("Santa fé")
						.numero("487").estado(EstadoEnum.MATO_GROSSO_DO_SUL).build())
				.build();

		ClienteEntity clienteSalvo = ClienteEntity.builder().id(14l).nome("ariel").cpf("000.000.000-00")
				.email("Ariel-edit@hotmail.com")
				.endereco(EnderecoEntity.builder().id(1l).cep("7901255").rua("Mundo da Lua").bairro("Santa fé")
						.numero("487").estado(EstadoEnum.MATO_GROSSO_DO_SUL).build())
				.build();

		BDDMockito.given(service.save(Mockito.any(ClienteEntity.class))).willReturn(clienteSalvo);

		String json = new ObjectMapper().writeValueAsString(novoClienteDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mockMvc.perform(request).andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/clientes/14"));

		// .andExpect( header().exists("/api/clientes/14") );
	}

	@Test
	@DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação do cliente")
	public void createInvalidClienteTest() throws Exception {
		String json = new ObjectMapper().writeValueAsString(new NovoClienteDto());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("errors", hasSize(6)));
	}

	@Test
	@DisplayName("Deve lançar erro ao tentar cadastrar um cliente com CPF já utilizado por outro")
	public void createClientWhithDuplicatedIsCpf() throws Exception {

		NovoClienteDto novoClienteDto = NovoClienteDto.builder().nome("ariel").cpf("111.111.111-11")
				.email("Ariel-edit@hotmail.com")
				.endereco(EnderecoDto.builder().id(1l).cep("7901255").rua("Mundo da Lua").bairro("Santa fé")
						.numero("487").estado(EstadoEnum.MATO_GROSSO_DO_SUL).build())
				.build();

		String json = new ObjectMapper().writeValueAsString(novoClienteDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		BDDMockito.given(service.save(Mockito.any(ClienteEntity.class)))
				.willThrow(new CpfJaCadastradoException("Cpf já cadastrado"));

		mockMvc.perform(request).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CpfJaCadastradoException))
				.andExpect(result -> assertEquals("Cpf já cadastrado", result.getResolvedException().getMessage()));

	}

	/*
	@Test
	@DisplayName("Deve lançar error, caso salve cliente com ID já existente")
	public void clienteIdDuplicadoExercao() throws Exception {

		ClienteDto novoClienteDto = ClienteDto.builder().nome("ariel").cpf("111.111.111-11")
				.email("Ariel-edit@hotmail.com")
				.endereco(EnderecoDto.builder().id(1l).cep("7901255").rua("Mundo da Lua").bairro("Santa fé")
						.numero("487").estado(EstadoEnum.MATO_GROSSO_DO_SUL).build())
				.build();

		String json = new ObjectMapper().writeValueAsString(novoClienteDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(CLIENTE_API)
													.contentType(MediaType.APPLICATION_JSON)
													.accept(MediaType.APPLICATION_JSON)
													.content(json);

		BDDMockito.given(service.findById(Mockito.anyLong()))
		.willThrow(new IdJaCadastradoException("Id já cadastrado"));
		
		
		mockMvc.perform(request).andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof IdJaCadastradoException))
		.andExpect(result -> assertEquals("Id já cadastrado", result.getResolvedException().getMessage()));

		
	}
	
	*/
}
