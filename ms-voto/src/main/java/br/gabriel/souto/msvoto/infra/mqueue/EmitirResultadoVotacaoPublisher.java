package br.gabriel.souto.msvoto.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmitirResultadoVotacaoPublisher {
    private final RabbitTemplate _rabbitTemplate;
    private final Queue _queueEmitirResultadoVotacao;

    @Autowired
    public EmitirResultadoVotacaoPublisher(RabbitTemplate rabbitTemplate, Queue queueEmitirResultadoVotacao) {
        _rabbitTemplate = rabbitTemplate;
        _queueEmitirResultadoVotacao = queueEmitirResultadoVotacao;
    }

    public void emitirVotacaoResultado(String mensagem) throws JsonProcessingException {
        String json = convertIntoJson(mensagem);
        _rabbitTemplate.convertAndSend(_queueEmitirResultadoVotacao.getActualName(), json);
    }

    private String convertIntoJson(String mensagem) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mensagem);
    }
}
