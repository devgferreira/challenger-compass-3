package br.gabriel.souto.msfuncionario.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    FUNCIONARIO_NAO_ENCONTRADO("Funcionario não foi encontrado"),
    FUNCIONARIO_JA_EXISTE("Funcionario já existe"),
    CPF_INVALIDO("CPF invalido, o CPF precisar conter 11 caracters");
    private final String message;

}