package br.com.vavive.clientes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	//configuração a quem tem permissão a ter acesso a nossa api. divindo por roles do usuario, a partir da URL
	//O /** tudo que estiver dentro da url a frente, preciso ter aquela determinada role
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/usuarios").permitAll()
			.antMatchers(     
					"/clientes/**", 
					"/servicos-prestados/**",
					"/usuarios/**",
					"/profissionais/**").authenticated()
			.anyRequest().denyAll();
		
//		.antMatchers("/vavive/api/clientes/**").hasAnyRole("USER", "ADM")

	}
}
