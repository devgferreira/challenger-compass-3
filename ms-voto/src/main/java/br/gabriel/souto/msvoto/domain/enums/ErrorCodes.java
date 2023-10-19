package br.gabriel.souto.msvoto.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    FUNCIONARIO_NAO_ENCONTRADO("Funcionario não foi encontrado"),
    PROPOSTA_NAO_ENCONTRADA("Proposta não encontrada."),
    NAO_PODE_VOTAR("O Funcionario não tem permissão para votar."),
    VOTACAO_ENCERRADA("A votação ja foi encerrada."),
    FUNCIONARIO_JA_VOTO("O funcionario ja votou nessa proposta.");
    private final String message;

}