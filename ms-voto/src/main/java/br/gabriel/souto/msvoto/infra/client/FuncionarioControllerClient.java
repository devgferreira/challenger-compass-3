package br.gabriel.souto.msvoto.infra.client;

import br.gabriel.souto.msvoto.domain.model.funcionario.Funcionario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-funcionario", path = "/funcionarios")
public interface FuncionarioControllerClient {
    @GetMapping("/{cpf}")
    Funcionario buscarFuncionarioPorCpf(@PathVariable String cpf);
}
