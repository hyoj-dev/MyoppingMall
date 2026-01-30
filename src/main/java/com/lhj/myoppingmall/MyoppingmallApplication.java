package com.lhj.myoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyoppingmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyoppingmallApplication.class, args);
	}

}
