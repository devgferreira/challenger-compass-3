package br.gabriel.souto.msvalidacaovoto.infra.client;

import br.gabriel.souto.msvalidacaovoto.domain.model.funcionario.FuncionarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-funcionario", path = "/funcionarios")
public interface FuncionarioControllerClient {
    @GetMapping("/{cpf}")
    FuncionarioResponse buscarFuncionarioPorCpf(@PathVariable String cpf);
}
