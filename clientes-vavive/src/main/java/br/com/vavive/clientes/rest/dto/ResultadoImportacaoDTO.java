package br.com.vavive.clientes.rest.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResultadoImportacaoDTO {

	private String entidade;
	private Integer quantidadeRegistrosNovos;
	private Integer quantidadeRegistrosAtualizados;
	private Integer quantidadeRegistrosRepetidos;
	
	private List<String> erros;
	
	@SuppressWarnings("unused")
	private ResultadoImportacaoDTO() { }
	
	public ResultadoImportacaoDTO(String entidade) {
		this.entidade = entidade;
		this.erros = new ArrayList<String>();
		this.quantidadeRegistrosNovos = 0;
		this.quantidadeRegistrosAtualizados = 0;
		this.quantidadeRegistrosRepetidos = 0;
	}
	
	public Integer getQuantidadeLinhasLidas() {
		return erros.size() + quantidadeRegistrosNovos + quantidadeRegistrosAtualizados + quantidadeRegistrosRepetidos;
	}

	public Integer getQuantidadeLinhasLidasSemErro() {
		return quantidadeRegistrosNovos + quantidadeRegistrosAtualizados + quantidadeRegistrosRepetidos;
	}

	public Integer getQuantidadeLinhasLidasComErro() {
		return erros.size();
	}

	public void addErro(String erro) {
		erros.add(erro);
	}

	public void incrementaQuantidadeRegistrosNovos() {
		quantidadeRegistrosNovos++;
	}

	public void incrementaQuantidadeRegistrosAtualizados() {
		quantidadeRegistrosAtualizados++;
	}

	public void incrementaQuantidadeRegistrosRepetidos() {
		quantidadeRegistrosRepetidos++;
	}

}
