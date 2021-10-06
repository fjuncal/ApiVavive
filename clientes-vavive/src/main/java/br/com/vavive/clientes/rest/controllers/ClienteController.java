package br.com.vavive.clientes.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.vavive.clientes.model.entity.Cliente;
import br.com.vavive.clientes.model.entity.ClienteFiltro;
import br.com.vavive.clientes.model.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@GetMapping
	public List<Cliente> obterTodos(){
		
		return repository.findAll();
	}
	
	@PostMapping("/search")
	public List<Cliente> obterTodos(@RequestBody(required = false) ClienteFiltro filtroCliente){
		if(filtroCliente == null) {
			return repository.findAll();
		}
		return repository.findAll(filtroCliente.getClausulaWhere());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente) {
		return repository.save(cliente);
	}
	
	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable Integer id) {
		return repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//método map -> recebe um objeto e retorno o objeto, buscar o cliente primeiro para dps deletar. Pq possibilita saber se o cliente não está na base, ele retorna o erro not found 
	public void deletar( @PathVariable Integer id ) {
		 repository
		.findById(id)
		.map(cliente -> {
			repository.delete(cliente);
			return Void.TYPE;
		})
		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar( @PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado) {
		 repository
			.findById(id)
			.map(cliente -> {
				clienteAtualizado.setId(cliente.getId());
				return repository.save(clienteAtualizado);
			})
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
	}
	
	@PostMapping("/importar")
	public void importarPlanilha(MultipartFile file) {
		System.out.println(file);
		
	}
	
}
