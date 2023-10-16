package br.gabriel.souto.msvoto;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableRabbit
@EnableFeignClients
public class MsVotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVotoApplication.class, args);
	}

}
