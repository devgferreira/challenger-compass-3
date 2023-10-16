package br.gabriel.souto.msvoto.infra.mqueue;

import br.gabriel.souto.msvoto.domain.model.proposta.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmitirPropostaResultadoPublisher {
    private final RabbitTemplate _rabbitTemplate;
    private final Queue _queueEmitirPropostaResultado;

    @Autowired
    public EmitirPropostaResultadoPublisher(RabbitTemplate rabbitTemplate, Queue queueEmitirPropostaResultado) {
        _rabbitTemplate = rabbitTemplate;
        _queueEmitirPropostaResultado = queueEmitirPropostaResultado;
    }

    public void emitirPropostaResultado(Proposta dados) throws JsonProcessingException {
        String json = convertIntoJson(dados);
        _rabbitTemplate.convertAndSend(_queueEmitirPropostaResultado.getActualName(), json);
    }

    private String convertIntoJson(Proposta dados) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dados);
    }
}
