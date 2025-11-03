package br.gov.ba.sesab.sala_espera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.gov.ba.sesab.sala_espera", "services"})
public class GerenciamentoDeSalasDeEsperaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {        
		SpringApplication.run(GerenciamentoDeSalasDeEsperaApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GerenciamentoDeSalasDeEsperaApplication.class);
	}

}
