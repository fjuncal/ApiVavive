package br.com.vavive.clientes.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.vavive.clientes.rest.dto.CriarSenhaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	@NotEmpty(message = "{campo.usuario.obrigatorio}")
	private String usuario;
	
	@Column
	@NotEmpty(message = "{campo.senha.obrigatorio}")
	@JsonIgnore
	private String senha;

	public Usuario(CriarSenhaDTO dto, PasswordEncoder encoder) {
		this.usuario = dto.getUsuario();
		this.senha = encoder.encode(dto.getSenha());
	}

}
