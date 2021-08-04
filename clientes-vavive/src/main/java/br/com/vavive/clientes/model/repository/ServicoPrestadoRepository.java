package br.com.vavive.clientes.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vavive.clientes.model.entity.ServicoPrestado;

public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, BigDecimal> {
	
	@Query(" select s from ServicoPrestado s join s.cliente c where upper( c.nome ) like upper( :nome ) and MONTH(s.dataAtividade) =:mes  ")
	List<ServicoPrestado> findByNomeClienteAndMes(@Param("nome")String nome, @Param("mes")Integer mes);

}
