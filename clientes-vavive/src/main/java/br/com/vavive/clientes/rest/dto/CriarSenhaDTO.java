package br.com.vavive.clientes.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriarSenhaDTO {

	private String usuario;
	private String senha;
}
