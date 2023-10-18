package br.gabriel.souto.msproposta.domain.model.Response;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropostaResponse {
    Long id;
    String titulo;
    String descricao;
    Setor setor;
    private boolean aberta;
    private String resultado;
    public PropostaResponse(PropostaDTO proposta) {
        id = proposta.getId();
        titulo = proposta.getTitulo();
        descricao = proposta.getDescricao();
        setor = proposta.getSetor();
        aberta = proposta.isAberta();
        resultado = proposta.getResultado();
    }
}
