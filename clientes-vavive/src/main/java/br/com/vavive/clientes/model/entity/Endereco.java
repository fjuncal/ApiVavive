package br.com.vavive.clientes.model.entity;

import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

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
	
	@Column(nullable = true, length = 9)
	private String cep;

	@Column(nullable = false, length = 100)
	private String logradouro;

	@Column(nullable = true, length = 100)
	private String complemento;

	@Column(nullable = false, length = 40)
	private String bairro;

	@Column(nullable = false, length = 40)
	private String municipio;
	
	@Column(nullable = false, length = 40)
	private String estado;

	@Column(name = "ponto_referencia", nullable = true)
	private String pontoDeReferencia;
	
	@Transient
	private String enderecoCompleto;
	
	public String getEnderecoCompleto() {
		String pattern = StringUtils.isEmpty(cep) ? "{0}, {1} - {2}, {3} - {4}." : "{0}, {1} - {2}, {3} - {4}, {5}.";
		return MessageFormat.format(pattern, logradouro, complemento, bairro, municipio, estado, cep);
	}
}
