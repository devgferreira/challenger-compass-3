package br.gabriel.souto.msvoto.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FuncionarioNaoEncontradoExeception extends RuntimeException{
    private ExceptionResponse exceptionResponse;

    public FuncionarioNaoEncontradoExeception(ExceptionResponse exceptionResponse) {
        super();
        this.exceptionResponse = exceptionResponse;
    }
}
