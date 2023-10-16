package br.gabriel.souto.msproposta.application.dtos;

import br.gabriel.souto.msproposta.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropostaDTO {
    Long id;
    String titulo;
    String descricao;
    Setor setor;
    private LocalTime tempo;
    private boolean aberta;
}
