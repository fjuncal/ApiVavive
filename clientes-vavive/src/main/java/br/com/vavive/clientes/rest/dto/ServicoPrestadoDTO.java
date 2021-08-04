package br.com.vavive.clientes.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {
	
	private String nome;
	private String observacao;
	private String dataAtividade;
	private String valor;
	private String duracao;
	private String hora;
	private Integer nota;
	private String responsavel; 
	private Integer idCliente;

}
