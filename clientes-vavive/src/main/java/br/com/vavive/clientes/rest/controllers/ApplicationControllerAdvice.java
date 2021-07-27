package br.com.vavive.clientes.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import br.com.vavive.clientes.rest.exception.ApiErrors;

//Classe que identifica um esteriotipo de classe que vao receber aquele erro e essa classe vai interceptar o erro e modificar o retorno da sua requisição
@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationErros( MethodArgumentNotValidException exception ) {
		BindingResult bindingResult = exception.getBindingResult();
		List<String> messages = bindingResult.getAllErrors()
			.stream()
			.map( ObjectError -> ObjectError.getDefaultMessage() )
			.collect(Collectors.toList());
		return new ApiErrors(messages);
	}
	
	//adicionando erro quando não encontra cliente(ResponseStatusException)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity handleResponseStatusException(ResponseStatusException exception) {
		String menssagemErro = exception.getMessage();
		HttpStatus codigoStatus = exception.getStatus();
		
		ApiErrors apiErrors = new ApiErrors(menssagemErro);
		
		return new ResponseEntity(apiErrors, codigoStatus);
	}

}
