package com.example.reminder.config;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
/*
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	@Qualifier("daoAuthenticationProvider")
	public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
	    this.authenticationProvider = authenticationProvider;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder(StrongPasswordEncryptor passwordEncryptor) {
    	PasswordEncoder passwordEncoder = new PasswordEncoder();
    	passwordEncoder.setPasswordEncryptor(passwordEncryptor);
    	return passwordEncoder;
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    
	@Autowired
	public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder) {
	    authenticationManagerBuilder.authenticationProvider(authenticationProvider);
	}
	*/
	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity
//		.authorizeRequests().antMatchers("/","/products","/product/show/*","/console/**").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .formLogin().loginPage("/login").permitAll()
//        .and()
//        .logout().permitAll();

		httpSecurity.csrf().disable();  // Is necessary for H2 DB access
		httpSecurity.headers().frameOptions().disable();
	}
	
	
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ADMIN")
		.and()
			.withUser("user").password("user").roles("USER");
		
		
    }
    
}
