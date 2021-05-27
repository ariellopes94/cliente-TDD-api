package com.ariellopes.clienteapi.exception.model;


public class CpfJaCadastradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CpfJaCadastradoException(String mensagem) {
		super(mensagem);
	}
	
	public CpfJaCadastradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}


