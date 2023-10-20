package br.gabriel.souto.msvalidacaovoto.infra.exceptions.handler;

import br.gabriel.souto.msvalidacaovoto.domain.enums.ErrorCodes;
import br.gabriel.souto.msvalidacaovoto.infra.exceptions.CpfInvalidoExeception;
import br.gabriel.souto.msvalidacaovoto.infra.exceptions.ExceptionResponse;
import br.gabriel.souto.msvalidacaovoto.infra.exceptions.PropostaNaoEncontradoExeception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CpfInvalidoExeception.class)
    public final ResponseEntity<Object> handleCpfInvalidoExeception(CpfInvalidoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.CPF_INVALIDO, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(PropostaNaoEncontradoExeception.class)
    public final ResponseEntity<Object> handlePropostaNaoEncontradoExeception(PropostaNaoEncontradoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.PROPOSTA_NAO_ENCONTRADA, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
