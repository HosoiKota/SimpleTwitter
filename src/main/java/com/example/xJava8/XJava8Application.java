package com.example.xJava8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("com.example.xJava8.repository.jpa")
@EnableJdbcRepositories("com.example.xJava8.repository.jdbc")
@SpringBootApplication
public class XJava8Application {

	public static void main(String[] args) {
		SpringApplication.run(XJava8Application.class, args);
	}

}
