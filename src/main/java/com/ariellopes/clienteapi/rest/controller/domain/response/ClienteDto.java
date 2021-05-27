package com.ariellopes.clienteapi.rest.controller.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
	
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private EnderecoDto endereco;

}
