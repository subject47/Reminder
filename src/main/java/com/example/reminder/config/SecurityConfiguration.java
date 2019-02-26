package com.example.reminder.config;

import java.util.Arrays;
import javax.sql.DataSource;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private Environment environment;

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
  public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
      UserDetailsService userDetailsService) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  @Autowired
  public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder.authenticationProvider(authenticationProvider);
  }

  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        .antMatchers("/", "/registration", "/products", "/product/show/*", "/console/**").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login").permitAll()
        .and().formLogin().defaultSuccessUrl("/index")
        .and().logout().permitAll();

    if (isDevEnv()) {
      httpSecurity.csrf().disable();  // Is necessary for H2 DB access
    }
    httpSecurity.headers().frameOptions().disable();
  }


  @Autowired
  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder
        .jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder)
        .usersByUsernameQuery("select username, encrypted_password, enabled from user where username = ?")
        .authoritiesByUsernameQuery(
            "select u.username, r.role from user u "
                + "inner join user_roles ur on u.id = ur.user_id "
                + "inner join role r on ur.roles_id = r.id "
                + "where u.username = ?");
  }

  private boolean isDevEnv() {
    return Arrays.asList(environment.getActiveProfiles()).contains("dev");
  }

}
