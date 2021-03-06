package br.com.vavive.clientes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.vavive.clientes.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService usuarioService;
	
	//AuthenticantionManager é e a classe que vai gerenciar a autenticação da aplicação, é através dele que a gnt sabe quem são os usuários da nossa aplicação e as suas senhas
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("juncal")
//			.password("123")
//			.roles("USER");
		auth
		.userDetailsService(usuarioService)
		.passwordEncoder(passwordEncoder()); //buscando o usuario no banco na classe UsuarioService
			
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//configuração de autorizar urls, controle de sessão, habilitar o CORS 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//csrf - recurso para aplicação web
		http
		.csrf().disable()
		.cors()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //A aplicação não vai manter sessão, controlar sessão através do token. Essa chamada desabilita a criação de sessão 
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
