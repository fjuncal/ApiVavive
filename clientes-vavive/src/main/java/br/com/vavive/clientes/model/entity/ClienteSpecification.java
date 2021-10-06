package br.com.vavive.clientes.model.entity;

import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

public interface ClienteSpecification {
	
	static Specification<Cliente> contemNome(String nome) {
		if(nome == null) {
			return where(null);
		}
		return (cliente, cq, cb) -> cb.like(cb.upper(cliente.get("nome")), "%" + nome.toUpperCase() + "%");
	}
	
	static Specification<Cliente> contemCpf(String cpf) {
		if(cpf == null) {
			return where(null);
		}
		return (cliente, cq, cb) -> cb.like(cliente.get("cpf"), "%" + cpf + "%");
	}

	static Specification<Cliente> temOrigem(String origem) {
		if(origem == null) {
			return where(null);
		}
		return (cliente, cq, cb) -> cb.like(cliente.get("origemCliente"), origem);
	}
	
	static Specification<Cliente> maiorQueDataCadastro(LocalDate dataInicio) {
		if(dataInicio == null) {
			return where(null);
		}
		return (cliente, cq, cb) -> cb.greaterThanOrEqualTo(cliente.get("dataCadastro"), dataInicio.atStartOfDay());
	}

	static Specification<Cliente> menorQueDataCadastro(LocalDate dataFim) {
		if(dataFim == null) {
			return where(null);
		}
		return (cliente, cq, cb) -> cb.lessThanOrEqualTo(cliente.get("dataCadastro"), dataFim.plusDays(1).atStartOfDay());
	}
}
