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
public class Endereco implements Comparable<Endereco>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = true, length = 9)
	private String cep;

	@Column(nullable = false)
	private String logradouro;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String complemento;

	@Column(nullable = false, length = 100)
	private String bairro;

	@Column(nullable = false, length = 40)
	private String municipio;
	
	@Column(nullable = false, length = 40)
	private String estado;

	@Column(name = "ponto_referencia", nullable = true, columnDefinition = "TEXT")
	private String pontoDeReferencia;
	
	@Transient
	private String enderecoCompleto;
	
	public String getEnderecoCompleto() {
		String rua = StringUtils.isEmpty(complemento) ? logradouro: logradouro + ", " + complemento;
		
		String pattern = StringUtils.isEmpty(cep) ? "{0} - {1}, {2} - {3}." : "{0} - {1}, {2} - {3}, {4}.";

		return MessageFormat.format(pattern, rua, bairro, municipio, estado, cep);
	}

	@Override
	public int compareTo(Endereco novoEndereco) {
		int logradouro = this.getLogradouro().contentEquals(novoEndereco.getLogradouro()) ? 0 : 10;
		int complemento = this.getComplemento().contentEquals(novoEndereco.getComplemento()) ? 0 : 1;
		return logradouro + complemento;
	}
	
	
}
