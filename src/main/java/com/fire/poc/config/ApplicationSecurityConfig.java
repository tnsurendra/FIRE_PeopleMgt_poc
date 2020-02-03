package com.fire.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
*/


//@Configuration
//@EnableWebSecurity
public class ApplicationSecurityConfig {
	
	
}
/*extends WebSecurityConfigurerAdapter {

	@Value("${fire.poc.user.name}")
	private String uName;
	@Value("${fire.poc.user.pwd}")
	private String pwd;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		
		 * httpSecurity.authorizeRequests().antMatchers("/person/**").access(
		 * "hasRole('USER')").anyRequest().authenticated().and().httpBasic();
		 

		
		 * httpSecurity.csrf().disable()
		 * .authorizeRequests().antMatchers("/person/**").authenticated()
		 * .and().httpBasic();
		 
		httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();

	}

	
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * PasswordEncoder
	 * enc=PasswordEncoderFactories.createDelegatingPasswordEncoder();
	 * System.out.println("$$$$$$$$$$$$$$$$$$$$$$"+uName+pwd);
	 * auth.inMemoryAuthentication().withUser(enc.encode(uName)).password(enc.encode
	 * (pwd)).roles("USER");
	 * 
	 * 
	 * }
	 
*/
