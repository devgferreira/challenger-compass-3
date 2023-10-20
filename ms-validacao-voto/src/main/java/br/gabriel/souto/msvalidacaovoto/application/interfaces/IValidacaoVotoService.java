package br.gabriel.souto.msvalidacaovoto.application.interfaces;

import br.gabriel.souto.msvalidacaovoto.domain.enums.ValidarVoto;

public interface IValidacaoVotoService {
    ValidarVoto validarVoto(Long propostaId, String funcionarioCpf);
}
