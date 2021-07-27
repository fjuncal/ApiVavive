package br.com.vavive.clientes.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class Atividade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String nome;
	
	@Column(nullable = true)
	private String plano;
	
	@Column(nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAtividade;
	
	@Column(nullable = false, updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro;
	
	@Column(nullable = false)
	private String duracao;
	
	@Column(nullable = false)
	private String hora;
	
	@Column(nullable = false)
	private Integer nota;
	
	@Column(nullable = true)
	private String observacao;
	
	@Column(nullable = false)
	private String responsavel;
	
	@Column(nullable = false)
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDate.now());
	}
	
}
