package com.annotation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.annotation.services.impl.UserDetailsServiceImpl;


/**
 * Security configuration class: authentications etc.
 * @author Luis
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	}
	 
	/**
	 * Http security such as url permissions, crsf etc.
	 */
    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
    
    	
    	security.cors().and().csrf().disable().addFilterAfter(new JWTAuthorizationFilter(userDetailsService) , UsernamePasswordAuthenticationFilter.class)
    	.authorizeRequests()
    	.antMatchers("/user/list").hasAnyAuthority("ROLE_ADMIN")
    	.antMatchers("/user/list/**").hasAnyAuthority("ROLE_ADMIN")
    	.antMatchers("/collections/all").hasAnyAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		.anyRequest().authenticated();
    }
    
//   
    
}