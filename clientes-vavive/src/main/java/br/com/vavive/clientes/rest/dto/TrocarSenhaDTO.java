package br.com.vavive.clientes.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrocarSenhaDTO {
	private String senhaAtual;
	private String senhaNova;
}
