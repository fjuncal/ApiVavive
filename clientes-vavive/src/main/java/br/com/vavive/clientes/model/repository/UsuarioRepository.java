package br.com.vavive.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vavive.clientes.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
