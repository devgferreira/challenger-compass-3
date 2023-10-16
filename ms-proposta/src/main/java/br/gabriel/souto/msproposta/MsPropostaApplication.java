package br.gabriel.souto.msproposta;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MsPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPropostaApplication.class, args);
	}

}
