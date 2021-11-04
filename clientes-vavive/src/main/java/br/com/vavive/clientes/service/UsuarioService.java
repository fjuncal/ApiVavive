package br.com.vavive.clientes.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vavive.clientes.exception.UsuarioCadastradoException;
import br.com.vavive.clientes.model.entity.Usuario;
import br.com.vavive.clientes.model.repository.UsuarioRepository;
import br.com.vavive.clientes.rest.dto.CriarSenhaDTO;
import br.com.vavive.clientes.rest.dto.TrocarSenhaDTO;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Usuario salvar(CriarSenhaDTO dto) {
		//utilizando o query method do spring data JPA
		boolean usuarioExiste = usuarioRepository.existsByUsuario(dto.getUsuario());
		if (usuarioExiste) {
			throw new UsuarioCadastradoException(dto.getUsuario());
		}
		
		
		Usuario usuario = new Usuario(dto, encoder);
		return usuarioRepository.save(usuario);
	}

	public Usuario recuperarUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return usuarioRepository.findByUsuario(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));
	}

	//carregar os usuarios do banco
	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		Usuario usuarioBanco = usuarioRepository.findByUsuario(usuario)
						.orElseThrow(() -> new UsernameNotFoundException("login não encontrado"));
		
		return User.builder()
					.username(usuarioBanco.getUsuario())
					.password(usuarioBanco.getSenha())
					.roles("USER")
					.build();
	}

	public void trocarSenha(@Valid TrocarSenhaDTO dto) {
		Usuario usuarioBanco = recuperarUsuarioLogado();
		
		if(!encoder.matches(dto.getSenhaAtual(), usuarioBanco.getSenha())) {
			throw new RuntimeException("Senha atual diferente da esperada.");
		}
		
		usuarioBanco.setSenha(encoder.encode(dto.getSenhaNova()));
		
		usuarioRepository.save(usuarioBanco);
	}
}
