package saude.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
@Profile("basic")
@Configuration
@EnableWebSecurity
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {

	     //libera somente consulta na urls declaradas aqui
			private static final String[] PUBLIC_MATCHERS_GET = {
				
					
			};
			
			//libera todos os metodos get, post, delete .etc nas urls declaradas aqui
			private static final String[] PUBLIC_MATCHERS_TODOS = {
					//"/usuarios/**"
			};
	
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("admin").password("admin").roles("ROLE");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
		.antMatchers(PUBLIC_MATCHERS_TODOS).permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().csrf().disable();
	}
	
	
	
}
