package br.com.vavive.clientes.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;
	
	private String observacao;
	
	@NotEmpty(message = "{campo.dataServico.obrigatorio}")
	private String dataAtividade;
	
	@NotEmpty(message = "{campo.valor.obrigatorio}")
	private String valor;
	
	@NotEmpty(message = "{campo.duracao.obrigatorio}")
	private String duracao;
	
	@NotEmpty(message = "{campo.hora.obrigatorio}")
	private String hora;
	
	@NotNull(message = "{campo.nota.obrigatorio}")
	private Integer nota;
	
	@NotEmpty(message = "{campo.responsavel.obrigatorio}")
	private String responsavel; 
	private Integer idCliente;

}
