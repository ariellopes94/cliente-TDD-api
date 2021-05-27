package com.ariellopes.clienteapi.rest.controller.domain.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ariellopes.clienteapi.rest.controller.domain.EstadoEnum;

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
public class EnderecoDto {
	
	private Long id;
	
	@NotBlank(message = "campo CEP não pode ser vazio")
	@NotNull(message = "campo CEP não pode ser nulo")
	@NotEmpty
	private String cep;
	
	private String rua;
	private String bairro;
	private String numero;
	private EstadoEnum estado;
	
}
