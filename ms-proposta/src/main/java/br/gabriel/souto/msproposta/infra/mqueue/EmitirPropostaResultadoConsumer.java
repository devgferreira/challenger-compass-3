package br.gabriel.souto.msproposta.infra.mqueue;

import br.gabriel.souto.msproposta.domain.interfaces.IPropostaRepository;
import br.gabriel.souto.msproposta.domain.model.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmitirPropostaResultadoConsumer {

    private final IPropostaRepository _propostaRepository;
    @Autowired
    public EmitirPropostaResultadoConsumer(IPropostaRepository propostaRepository) {
        _propostaRepository = propostaRepository;
    }

    @RabbitListener(queues = "${mq.queues.emitir-proposta-resultado-votacao}" )
    public void receberResultadoVotacao(@Payload String payload){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Proposta dados = mapper.readValue(payload, Proposta.class);
            Proposta proposta = _propostaRepository.findById(dados.getId()).orElseThrow();
            proposta.setResultado(dados.getResultado());
            _propostaRepository.save(proposta);
        } catch (Exception e) {
            log.error("Erro ao receber solicitacao de emitir-proposta-resultado-votacao: {}", e.getMessage());
        }
    }
}
