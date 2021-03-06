package br.com.vavive.clientes.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vavive.clientes.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByUsuario(String usuario);

	//query method - select count(*) > 0 from usuario where usuario = :usuario
	boolean existsByUsuario(String usuario);

}
