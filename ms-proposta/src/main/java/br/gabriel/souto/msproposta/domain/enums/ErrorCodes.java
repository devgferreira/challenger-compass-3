package br.gabriel.souto.msproposta.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    PROPOSTA_NAO_ENCONTRADA("Proposta não foi encontrado");
    private final String message;

}