package br.gabriel.souto.msvalidacaovoto.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
    CPF_INVALIDO("CPF Não Encontrado"),
    PROPOSTA_NAO_ENCONTRADA("Proposta Não Encontrado");
    private final String message;

}