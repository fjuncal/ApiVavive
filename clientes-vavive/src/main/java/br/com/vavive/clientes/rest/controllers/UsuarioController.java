package br.com.vavive.clientes.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vavive.clientes.exception.UsuarioCadastradoException;
import br.com.vavive.clientes.model.entity.Usuario;
import br.com.vavive.clientes.rest.dto.CriarSenhaDTO;
import br.com.vavive.clientes.rest.dto.TrocarSenhaDTO;
import br.com.vavive.clientes.service.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public void salvar(@RequestBody @Valid CriarSenhaDTO dto) {
		try {
			usuarioService.salvar(dto);
		} catch (UsuarioCadastradoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	@PostMapping("/trocarSenha")
	public void trocarSenha(@RequestBody @Valid TrocarSenhaDTO dto) {
		usuarioService.trocarSenha(dto);
	}
	
	@GetMapping
	public String usuarioLogado() {
		Usuario user = usuarioService.recuperarUsuarioLogado();
		return user.getUsuario();
	}
}
