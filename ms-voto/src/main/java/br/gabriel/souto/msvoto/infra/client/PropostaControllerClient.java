package br.gabriel.souto.msvoto.infra.client;

import br.gabriel.souto.msvoto.domain.model.proposta.Proposta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "ms-proposta", path = "/propostas")
public interface PropostaControllerClient {
    @GetMapping("/{id}")
    Proposta buscarPropostaPorId(@PathVariable Long id);

    @GetMapping
    List<Proposta> buscarTodasAsPropostas();
}
