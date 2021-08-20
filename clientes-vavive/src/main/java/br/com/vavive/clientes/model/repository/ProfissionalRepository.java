package br.com.vavive.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vavive.clientes.model.entity.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {

}
