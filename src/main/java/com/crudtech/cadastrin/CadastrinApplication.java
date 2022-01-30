package com.crudtech.cadastrin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"com.crudtech.cadastrin"})
public class CadastrinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastrinApplication.class, args);
	}
}
