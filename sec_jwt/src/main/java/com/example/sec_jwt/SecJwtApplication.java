package com.example.sec_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SecJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecJwtApplication.class, args);
	}

}
