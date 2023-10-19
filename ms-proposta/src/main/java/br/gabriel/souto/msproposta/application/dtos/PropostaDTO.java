package br.gabriel.souto.msproposta.application.dtos;

import br.gabriel.souto.msproposta.domain.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class PropostaDTO {
    Long id;
    String titulo;
    String descricao;
    Setor setor;
    private LocalTime tempo;
    private boolean aberta;
    private String resultado;

    public PropostaDTO(Long id, String titulo, String descricao, Setor setor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.setor = setor;
    }
}
