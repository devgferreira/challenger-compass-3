package br.gabriel.souto.msvoto.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VotoInvalidoExeception extends RuntimeException {
    private ExceptionResponse exceptionResponse;

    public VotoInvalidoExeception(ExceptionResponse exceptionResponse) {
        super();
        this.exceptionResponse = exceptionResponse;
    }
}
