package br.gabriel.souto.msfuncionario.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FuncionarioJaExisteExeception extends RuntimeException{
    private ExceptionResponse exceptionResponse;

    public FuncionarioJaExisteExeception(ExceptionResponse exceptionResponse) {
        super();
        this.exceptionResponse = exceptionResponse;
    }
}
