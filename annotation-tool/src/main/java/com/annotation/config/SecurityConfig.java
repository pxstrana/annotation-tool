package com.annotation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Security configuration class
 * @author Luis Pastrana
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	}
	 
	/**
	 * Configuration of several security aspects
	 */
    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
    
    	
    	security.cors().and().csrf().disable().addFilterAfter(new JWTAuthorizationFilter(userDetailsService) , UsernamePasswordAuthenticationFilter.class)
    	.authorizeRequests()
    	.antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN")
    	.antMatchers("/collections/**").hasAnyAuthority("ROLE_ADMIN")
    	.antMatchers("/collections/me").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
    	.antMatchers("/layer/*").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
    	.antMatchers("/layer/add").hasAnyAuthority("ROLE_ADMIN")
    	.antMatchers("/layer/delete/*").hasAnyAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		.anyRequest().authenticated();
    }
    
    
    
}