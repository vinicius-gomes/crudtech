package com.crudtech.cadastrin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CadastrinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastrinApplication.class, args);
	}
}
