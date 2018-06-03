package com.example.reminder.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * http://localhost:8080/console/
 * 
 * Value			Setting
 * Driver Class		org.h2.Driver
 * JDBC URL			jdbc:h2:mem:testdb
 * User Name		sa
 * Password	 		<blank>
 * */


@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.reminder.domain"})
@EnableJpaRepositories(basePackages = {"com.example.reminder.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}