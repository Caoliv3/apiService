package br.com.boavista.tubosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TubospApplication {

	public static void main(String[] args) {
		SpringApplication.run(TubospApplication.class, args);
	}
}
