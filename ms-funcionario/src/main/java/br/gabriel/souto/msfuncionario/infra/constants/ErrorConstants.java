package br.gabriel.souto.msfuncionario.infra.constants;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class ErrorConstants {
    public static final String FUNCIONARIO_NAO_ENCONTRADO = "[MS-FUNCIONARIO] Funcionario não foi encontrado";
    public static final String FUNCIONARIO_JA_EXISTE = "[MS-FUNCIONARIO] Funcionario já existe";
    public static final String CPF_INVALIDO = "[MS-FUNCIONARIO] CPF invalido, o cpf precisa conter 11 caracters";
}
