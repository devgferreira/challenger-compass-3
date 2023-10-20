package br.gabriel.souto.msvalidacaovoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsValidacaoVotoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsValidacaoVotoApplication.class, args);
    }

}
