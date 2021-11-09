package br.com.vavive.clientes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.model.entity.ClienteSpecification;
import br.com.vavive.clientes.model.entity.Usuario;
import br.com.vavive.clientes.model.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Cliente salvar(Cliente cliente) {
		Usuario usuario = usuarioService.recuperarUsuarioLogado();
		cliente.setCriador(usuario);
		return repository.save(cliente);
	}
	
	public Cliente consultarPorNome(String nome) {
		Optional<Cliente> result = repository.findOne(ClienteSpecification.igualNome(nome));
		return result.isPresent() ? result.get() : null;
	}
}
