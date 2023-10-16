package br.gabriel.souto.msvoto.domain.model.voto;

import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String funcionarioCpf;
    private Long propostaId;
    @Enumerated(value = EnumType.STRING)
    private VotoStatus status;

}
