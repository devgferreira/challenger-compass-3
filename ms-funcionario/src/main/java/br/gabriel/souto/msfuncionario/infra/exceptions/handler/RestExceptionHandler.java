package br.gabriel.souto.msfuncionario.infra.exceptions.handler;

import br.gabriel.souto.msfuncionario.domain.enums.ErrorCodes;
import br.gabriel.souto.msfuncionario.infra.exceptions.CpfInvalidoExeception;
import br.gabriel.souto.msfuncionario.infra.exceptions.ExceptionResponse;
import br.gabriel.souto.msfuncionario.infra.exceptions.FuncionarioJaExisteExeception;
import br.gabriel.souto.msfuncionario.infra.exceptions.FuncionarioNaoEncontradoExeception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FuncionarioNaoEncontradoExeception.class)
    public final ResponseEntity<Object> handleFuncionarioNaoEncontradoExeception(FuncionarioNaoEncontradoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.FUNCIONARIO_NAO_ENCONTRADO, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(FuncionarioJaExisteExeception.class)
    public final ResponseEntity<Object> handleFuncionarioJaExisteExeception(FuncionarioJaExisteExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.FUNCIONARIO_JA_EXISTE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(exceptionResponse);
    }
    @ExceptionHandler(CpfInvalidoExeception.class)
    public final ResponseEntity<Object> handleCpfInvalidoExeception(CpfInvalidoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.CPF_INVALIDO, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
