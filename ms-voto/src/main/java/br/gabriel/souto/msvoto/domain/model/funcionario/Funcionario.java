package br.gabriel.souto.msvoto.domain.model.funcionario;

import br.gabriel.souto.msvoto.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    private Long id;
    private String cpf;
    private String nome;
    private Setor setor;
}
