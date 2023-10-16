package br.gabriel.souto.msproposta.domain.model;

import br.gabriel.souto.msproposta.domain.enums.Setor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    @Enumerated(value = EnumType.STRING)
    private Setor setor;
    private LocalTime tempo;
    private boolean aberta;

    public Proposta(Long id, String titulo, String descricao, Setor setor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.setor = setor;
    }
}
