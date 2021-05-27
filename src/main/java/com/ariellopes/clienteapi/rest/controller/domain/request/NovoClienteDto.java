package com.ariellopes.clienteapi.rest.controller.domain.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ariellopes.clienteapi.rest.controller.domain.response.EnderecoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NovoClienteDto {
	
	@NotBlank(message = "campo nome nao pode ser vazio")
	@NotNull(message = "campo nome nao pode ser nulo")
	private String nome;
	
	@NotBlank(message = "campo CPF nao pode ser vazio")
	@NotNull(message = "campo CPF nao pode ser nulo")
	private String cpf;
	
	@NotBlank(message = "campo email nao pode ser vazio")
	@NotNull(message = "campo email nao pode ser nulo")
	private String email;
	
	@Valid
	private EnderecoDto endereco;


}
