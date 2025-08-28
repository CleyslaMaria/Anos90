package com.noventoteca.Anos90;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Esta anotação é o ponto de partida de uma aplicação Spring Boot
 * Ela habilita a configuração automática, o escaneamento de componentes 
 * e aconfiguração de beans, fazendo com que sua aplicação funcione */
@SpringBootApplication
public class Anos90Application {

	/** O método main é o ponto de entrada de qualquer aplicação Java.
	 * 	Ele usa o SpringApplication.run para iniciar a sua aplicação Spring Boot,
	 * que cria o contexto da aplicação e inicia o servidor
	*/
	public static void main(String[] args) {
		SpringApplication.run(Anos90Application.class, args);
	}

}
