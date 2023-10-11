package br.gabriel.souto.msproposta.application.dtos;

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
}
