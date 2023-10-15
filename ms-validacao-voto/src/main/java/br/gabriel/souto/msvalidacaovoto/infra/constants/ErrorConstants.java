package br.gabriel.souto.msvalidacaovoto.infra.constants;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class ErrorConstants {
    public static final String CPF_INVALIDO = "[MS-VALIDACAO_VOTO] CPF Não Encontrado";
    public static final String PROPOSTA_NAO_ENCONTRADA = "[MS-VALIDACAO_VOTO] Proposta Não Encontrado";
}
