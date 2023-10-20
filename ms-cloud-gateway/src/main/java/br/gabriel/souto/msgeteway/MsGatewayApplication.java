package br.gabriel.souto.msgeteway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/funcionarios/**").uri("lb://ms-funcionario"))
                .route(r -> r.path("/propostas/**").uri("lb://ms-proposta"))
                .route(r -> r.path("/validacao-votos/**").uri("lb://ms-validacao-voto"))
                .route(r -> r.path("/votos/**").uri("lb://ms-voto"))
                .build();
    }
}
