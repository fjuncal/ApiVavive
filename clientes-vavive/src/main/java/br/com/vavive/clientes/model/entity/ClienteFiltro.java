package br.com.vavive.clientes.model.entity;

import static br.com.vavive.clientes.model.entity.ClienteSpecification.contemCpf;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.contemNome;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.maiorQueDataCadastro;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.menorQueDataCadastro;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.temOrigem;
import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ClienteFiltro {

	private String nome;
	
	private String cpf;
	
	private String origemCliente;
		
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicioCadastro;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFimCadastro;
	
	public Specification<Cliente> getClausulaWhere() {
		return where(contemNome(nome))
				.and(contemCpf(cpf))
				.and(temOrigem(origemCliente))
				.and(maiorQueDataCadastro(dataInicioCadastro))
				.and(menorQueDataCadastro(dataFimCadastro));
	}
}

