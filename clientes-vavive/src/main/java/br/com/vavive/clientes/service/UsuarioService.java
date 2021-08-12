package br.com.vavive.clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.vavive.clientes.model.entity.Usuario;
import br.com.vavive.clientes.model.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
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

}
