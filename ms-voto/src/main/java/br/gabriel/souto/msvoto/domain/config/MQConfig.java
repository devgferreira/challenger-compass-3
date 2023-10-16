package br.gabriel.souto.msvoto.domain.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Value("${mq.queues.emitir-proposta-resultado-votacao}")
    private String emitirPropostaResultadoVotacaoFila;
    @Bean
    public Queue queueEmitirPropostaResultado(){
        return new Queue(emitirPropostaResultadoVotacaoFila, true);
    }
}
