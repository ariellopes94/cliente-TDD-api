package com.ariellopes.clienteapi.rest.controller.domain;

import lombok.Getter;

@Getter
public enum EstadoEnum {

	MATO_GROSSO_DO_SUL(01, "Mato Grosso do sul"),
	SAO_PAULO(02, "São Paulo"),
	RIO_DE_JANEIRO(03, "Rio De Janeiro");
	
	private int codigo;
	private String descricao;
	
	private EstadoEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
}
