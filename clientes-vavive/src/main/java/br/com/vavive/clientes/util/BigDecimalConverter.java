package br.com.vavive.clientes.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

//Anotacao para fazer a injeçao de dependencia
@Component
public class BigDecimalConverter {
		
	public BigDecimal converterBigDecimal(String valor) {
		if (valor == null) {
			return null;
		}
		valor = valor.replace(".", "").replace(",", ".");
		return new BigDecimal(valor);
	}

}
