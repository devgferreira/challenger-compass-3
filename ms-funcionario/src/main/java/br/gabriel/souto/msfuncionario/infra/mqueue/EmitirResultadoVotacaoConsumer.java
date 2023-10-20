package br.gabriel.souto.msfuncionario.infra.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmitirResultadoVotacaoConsumer {
    @RabbitListener(queues = "${mq.queues.emitir-resultado-votacao}")
    public void receberResultadoVotacao(@Payload String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String mensagem = mapper.readValue(payload, String.class);
            log.info(mensagem);

        } catch (Exception e) {
            log.error("Erro ao receber solicitacao de emitir-resultado-votacao: {}", e.getMessage());
        }
    }
}
