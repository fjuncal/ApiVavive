package br.com.vavive.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vavive.clientes.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
