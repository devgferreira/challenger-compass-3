package br.gabriel.souto.msfuncionario.domain.model.funcionario.response;

import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponse {

    Long id;
    String cpf;
    String nome;

    public FuncionarioResponse(FuncionarioDTO funcionarioDTO) {
        id =funcionarioDTO.getId();
        cpf=funcionarioDTO.getCpf();
        nome=funcionarioDTO.getNome();
    }
}
