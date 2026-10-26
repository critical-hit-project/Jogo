package com.critcal_hit.jogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.critical_hit.jogo", "com.critical_hit.lib"})
@SpringBootApplication
public class JogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JogoApplication.class, args);
	}

}
