package br.com.vavive.clientes.rest.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

//classe para padronizar os erros da api como uma lista
public class ApiErrors {
	
	@Getter
	private List<String> errors;

	public ApiErrors(List<String> errors) {
		this.errors = errors;
	}

	public ApiErrors(String message) {
		this.errors = Arrays.asList(message);
	}
	
	

}
