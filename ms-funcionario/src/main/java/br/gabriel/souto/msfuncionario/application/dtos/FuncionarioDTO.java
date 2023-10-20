package br.gabriel.souto.msfuncionario.application.dtos;


import br.gabriel.souto.msfuncionario.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    Long id;
    String cpf;
    String nome;
    Setor setor;
}
