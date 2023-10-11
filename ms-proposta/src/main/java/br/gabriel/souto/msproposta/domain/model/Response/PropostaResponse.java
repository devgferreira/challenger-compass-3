package br.gabriel.souto.msproposta.domain.model.Response;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropostaResponse {
    Long id;
    String titulo;
    String descricao;

    public PropostaResponse(PropostaDTO proposta) {
        id = proposta.getId();
        titulo = proposta.getTitulo();
        descricao = proposta.getDescricao();
    }
}
