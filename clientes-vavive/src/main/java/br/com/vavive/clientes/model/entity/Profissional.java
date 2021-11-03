package br.com.vavive.clientes.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Entity
@Data
public class Profissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String nome;
	
	@Column(nullable = true, length = 11)
	@CPF(message = "{campo.cpf.invalido}")
	private String cpf;
	
	@Column(nullable = true, length = 11)
	private String rg;
	
	@Column(nullable = false, length = 13)
	@NotEmpty(message =  "{campo.telefone.obrigatorio}")
	private String telefone;
	
	@Column(nullable = false)
	@NotEmpty(message = "{campo.endereco.obrigatorio}")
	private String endereco;
	
	@Column(nullable = false)
	@NotEmpty(message = "{campo.cep.obrigatorio}")
	private String cep;
	
	@Column(nullable = false, length = 40)
	@NotEmpty(message = "{campo.bairro.obrigatorio}")
	private String bairro;
	
	@Column(nullable = true)
	private String dadosBancarios;

	@Column(nullable = true)
	private String disponibilidade;

	@Column(nullable = true)
	private Integer nota;

	@Column(nullable = true)
	private String observacao;

	@Column(nullable = true)
	private String tipoServico;
	
	@Column(nullable = false)
	private String aprovado;

	@Column(nullable = false)
	private boolean ativo;
	
	@Column(nullable = false)
	private String referenciaProfissional;

	@Basic(fetch = FetchType.LAZY, optional = true)
	@Column(name = "FOTO", nullable = true)
	private byte[] foto;

	@Basic(fetch = FetchType.LAZY, optional = true)
	@Column(name = "ANTECEDENTE_CRIMINAL", nullable = true)
	private byte[] antecedenteCriminal;
}
