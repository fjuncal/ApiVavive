package br.com.vavive.clientes.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vavive.clientes.model.entity.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, BigDecimal> {

}
