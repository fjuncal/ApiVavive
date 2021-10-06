package br.com.vavive.clientes.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String cep;

	@Column(nullable = false, length = 40)
	private String logradouro;

	@Column(nullable = false, length = 40)
	private String complemento;

	@Column(nullable = false, length = 40)
	private String municipio;

	@Column(nullable = false, length = 40)
	private String bairro;
	
	@Column(nullable = false, length = 40)
	private String estado;

	@Column(name = "ponto_referencia", nullable = true, length = 100)
	private String pontoDeReferencia;
	
	@Transient
	private String enderecoCompleto;
	
	public String getEnderecoCompleto() {
		return logradouro + " " + complemento + " - " + bairro;
	}
}
