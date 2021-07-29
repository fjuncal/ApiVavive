package br.com.vavive.clientes.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;
		
	@Column(nullable = true, length = 11)
	@CPF(message = "{campo.cpf.invalido}")
	private String cpf;
	
	@Column(nullable = true, length = 70)
	@Email(message = "{campo.email.invalido}")
	private String email;
	
	@Column(nullable = false)
	@NotEmpty(message = "{campo.endereco.obrigatorio}")
	private String endereco;
	
	@Column(nullable = false)
	@NotEmpty(message = "{campo.cep.obrigatorio}")
	private String cep;
	
	@Column(nullable = false, length = 40)
	@NotEmpty(message = "{campo.bairro.obrigatorio}")
	private String bairro;
	
	@Column(nullable = false, length = 40)
	@NotEmpty(message = "{campo.municipio.obrigatorio}")
	private String municipio;
	
	@Column(nullable = false, length = 40)
	@NotEmpty(message = "{campo.estado.obrigatorio}")
	private String estado;
	
	@Column(nullable = false, length = 13)
	@NotEmpty(message =  "{campo.telefone.obrigatorio}")
	private String telefone;
	
	@Column(nullable = true)
	private String observacao;
	
	@Column(nullable = true)
	@JoinColumn(name = "ponto_referencia")
	private String pontoDeReferencia;
	
	@Column(nullable = true)
	@JoinColumn(name = "origem_cliente")
	private String origemCliente;
		
	@Column(nullable = false, updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy  HH:mm:ss")
	private LocalDateTime dataCadastro;
	
	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDateTime.now());
	}
	
	
}
