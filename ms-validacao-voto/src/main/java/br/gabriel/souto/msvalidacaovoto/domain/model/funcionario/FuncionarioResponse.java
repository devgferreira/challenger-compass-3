package br.gabriel.souto.msvalidacaovoto.domain.model.funcionario;

import br.gabriel.souto.msvalidacaovoto.domain.enums.Setor;
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
    Setor setor;
}