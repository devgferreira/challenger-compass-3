package br.gabriel.souto.msproposta.infra.exceptions.handler;

import br.gabriel.souto.msproposta.domain.enums.ErrorCodes;
import br.gabriel.souto.msproposta.infra.exceptions.ExceptionResponse;
import br.gabriel.souto.msproposta.infra.exceptions.PropostaNaoEncontradoExeception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PropostaNaoEncontradoExeception.class)
    public final ResponseEntity<Object> handlePropostaNaoEncontradoExeception(PropostaNaoEncontradoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.PROPOSTA_NAO_ENCONTRADA, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

}
