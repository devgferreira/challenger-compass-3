package br.gabriel.souto.msvalidacaovoto.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PropostaNaoEncontradoExeception extends RuntimeException{
    private ExceptionResponse exceptionResponse;

    public PropostaNaoEncontradoExeception(ExceptionResponse exceptionResponse) {
        super();
        this.exceptionResponse = exceptionResponse;
    }
}
