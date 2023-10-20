package br.gabriel.souto.msvalidacaovoto.domain.model.proposta;

import br.gabriel.souto.msvalidacaovoto.domain.enums.Setor;
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
    Setor setor;
}
