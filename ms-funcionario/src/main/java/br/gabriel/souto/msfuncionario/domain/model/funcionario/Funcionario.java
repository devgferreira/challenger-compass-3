package br.gabriel.souto.msfuncionario.domain.model.funcionario;

import br.gabriel.souto.msfuncionario.domain.enums.Setor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String cpf;
    String nome;
    @Enumerated(value = EnumType.STRING)
    Setor setor;


}
