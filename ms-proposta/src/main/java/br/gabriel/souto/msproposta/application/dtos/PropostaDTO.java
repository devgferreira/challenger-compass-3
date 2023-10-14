package br.gabriel.souto.msproposta.application.dtos;

import br.gabriel.souto.msproposta.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropostaDTO {
    Long id;
    String titulo;
    String descricao;
    Setor setor;
}
