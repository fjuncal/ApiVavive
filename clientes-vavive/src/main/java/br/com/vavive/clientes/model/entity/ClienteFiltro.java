package br.com.vavive.clientes.model.entity;

import static br.com.vavive.clientes.model.entity.ClienteSpecification.contemCpf;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.contemEndereco;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.contemNome;
import static br.com.vavive.clientes.model.entity.ClienteSpecification.contemTelefone;
import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class ClienteFiltro {

	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private String endereco;

//	private String origemCliente;
//
//	@JsonFormat(pattern = "dd/MM/yyyy")
//	private LocalDate dataInicioCadastro;
//
//	@JsonFormat(pattern = "dd/MM/yyyy")
//	private LocalDate dataFimCadastro;
	
	public Specification<Cliente> getClausulaWhere() {
		return where(contemNome(nome))
				.and(contemCpf(cpf))
				.and(contemTelefone(telefone)
				.and(contemEndereco(endereco)));
	}
}

