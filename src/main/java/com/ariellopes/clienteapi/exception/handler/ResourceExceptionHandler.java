package com.ariellopes.clienteapi.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ariellopes.clienteapi.exception.handler.validation.StandardError;
import com.ariellopes.clienteapi.exception.handler.validation.ValidationError;
import com.ariellopes.clienteapi.exception.model.CpfJaCadastradoException;
import com.ariellopes.clienteapi.exception.model.IdJaCadastradoException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError erro = new ValidationError();

		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			erro.addError(x.getField(), x.getDefaultMessage());
		}

		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(400);
		erro.setError("Erro de Validação");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(CpfJaCadastradoException.class)
	public ResponseEntity<StandardError> cpfjaCadastradoException(CpfJaCadastradoException e,
			                                                            HttpServletRequest request){
		StandardError erro = new StandardError();
		
		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(400);
		erro.setError("Cliente Não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(IdJaCadastradoException.class)
	public ResponseEntity<StandardError> idJaCadastradoException(CpfJaCadastradoException e,
			                                                            HttpServletRequest request){
		StandardError erro = new StandardError();
		
		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(400);
		erro.setError("Cliente Não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
