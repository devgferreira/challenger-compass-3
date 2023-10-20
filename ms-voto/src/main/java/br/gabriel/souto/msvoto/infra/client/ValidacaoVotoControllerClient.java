package br.gabriel.souto.msvoto.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-validacao-voto", path = "/validacao-votos")
public interface ValidacaoVotoControllerClient {
    @GetMapping("propostas/{propostaId}/funcionarios/{funcionarioCpf}")
    String validarVoto(@PathVariable Long propostaId, @PathVariable String funcionarioCpf);
}
