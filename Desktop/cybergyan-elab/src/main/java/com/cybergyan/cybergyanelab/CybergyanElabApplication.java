package com.cybergyan.cybergyanelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.cybergyan.cybergyanelab")
@EntityScan("com.cybergyan.cybergyanelab.entities")
@EnableJpaRepositories("com.cybergyan.cybergyanelab.repository")
public class CybergyanElabApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CybergyanElabApplication.class, args);
	}
}
/**
 * Ho gya.....
 */