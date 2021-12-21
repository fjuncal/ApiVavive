package br.com.vavive.clientes.rest.controllers;

import static br.com.vavive.clientes.service.planilha.entity.TipoPlanilhaEnum.FUNCIONARIO;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.vavive.clientes.model.entity.Profissional;
import br.com.vavive.clientes.model.repository.ProfissionalRepository;
import br.com.vavive.clientes.rest.dto.ResultadoImportacaoDTO;
import br.com.vavive.clientes.service.planilha.PlanilhaService;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
	
	@Autowired
	private ProfissionalRepository repository;
	
	@Autowired
	private PlanilhaService planilhaService;
	
	@GetMapping()
	public List<Profissional> obterTodos(){
		return repository.findAll();
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Profissional salvar(@RequestBody @Valid Profissional profissional) {
		return repository.save(profissional);
	}
	
	@GetMapping("{id}")
	public Profissional acharPorId(@PathVariable Integer id) {
		return repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado") );
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//método map -> recebe um objeto e retorno o objeto, buscar o profissional primeiro para dps deletar. Pq possibilita saber se o profissional não está na base, ele retorna o erro not found 
	public void deletar( @PathVariable Integer id ) {
		 repository
		.findById(id)
		.map(profissional -> {
			repository.delete(profissional);
			return Void.TYPE;
		})
		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado") );
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar( @PathVariable Integer id, @RequestBody @Valid Profissional profissionalAtualizado) {
		 repository
			.findById(id)
			.map(profissional -> {
				profissionalAtualizado.setId(profissional.getId());
				return repository.save(profissionalAtualizado);
			})
			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado") );
	}
	
	@PostMapping("/importar")
	public ResultadoImportacaoDTO importarPlanilha(@RequestParam("arquivo") MultipartFile file) throws IOException {
		System.out.println("bateu aqui");
		return planilhaService.importar(file, FUNCIONARIO);
	}
}
