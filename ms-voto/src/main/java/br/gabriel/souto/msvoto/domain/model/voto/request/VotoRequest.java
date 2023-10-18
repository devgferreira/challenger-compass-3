package br.gabriel.souto.msvoto.domain.model.voto.request;

import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import lombok.Data;

@Data
public class VotoRequest {
    private VotoStatus status;
}
