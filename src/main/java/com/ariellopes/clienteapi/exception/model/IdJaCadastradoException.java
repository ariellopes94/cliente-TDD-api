package com.ariellopes.clienteapi.exception.model;


public class IdJaCadastradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IdJaCadastradoException(String mensagem) {
		super(mensagem);
	}
	
	public IdJaCadastradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}


