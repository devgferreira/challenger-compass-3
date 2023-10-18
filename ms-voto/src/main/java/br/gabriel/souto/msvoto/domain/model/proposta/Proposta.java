package br.gabriel.souto.msvoto.domain.model.proposta;

import br.gabriel.souto.msvoto.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proposta {
    private Long id;
    private String titulo;
    private String descricao;
    private Setor setor;
    private LocalTime tempo;
    private boolean aberta;
    private String resultado;
}
