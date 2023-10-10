package br.gabriel.souto.msfuncionario.domain.model.funcionario;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario {
    Long id;
    String cpf;
    String nome;

}
