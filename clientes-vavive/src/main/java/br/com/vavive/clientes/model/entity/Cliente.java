package br.com.vavive.clientes.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude="servicosPrestados")
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
	
	@Column(nullable = false, length = 13)
	@NotEmpty(message =  "{campo.telefone.obrigatorio}")
	private String telefone;

	@Column(nullable = false, length = 13)
	@NotEmpty(message =  "{campo.telefone.obrigatorio}")
	private String telefone2;

	@Column(nullable = false, length = 13)
	@NotEmpty(message =  "{campo.telefone.obrigatorio}")
	private String telefone3;

	@Column(nullable = true)
	private String observacao;
	
	@Column(name = "origem_cliente", nullable = true)
	private String origemCliente;
		
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Endereco> enderecos;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private Set<ServicoPrestado> servicosPrestados;

	@Column(nullable = false, updatable = false)
	private boolean ativo;

	@Column(nullable = false, updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy  HH:mm:ss")
	private LocalDateTime dataCadastro;

	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDateTime.now());
		setAtivo(true);
	}
	
	
}
