package br.gabriel.souto.msvalidacaovoto.infra.client;

import br.gabriel.souto.msvalidacaovoto.domain.model.proposta.PropostaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-proposta", path = "/propostas")
public interface PropostaControllerClient {
    @GetMapping("/{id}")
    PropostaResponse buscarPropostaPorId(@PathVariable Long id);
}
