package br.gabriel.souto.msvoto.infra.exceptions.handler;

import br.gabriel.souto.msvoto.domain.enums.ErrorCodes;
import br.gabriel.souto.msvoto.infra.exceptions.*;
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
    @ExceptionHandler(PropostaNaoEncontradoExeception.class)
    public final ResponseEntity<Object> handlePropostaNaoEncontradoExeception(PropostaNaoEncontradoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.PROPOSTA_NAO_ENCONTRADA, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(FuncionarioJaVotoExeception.class)
    public final ResponseEntity<Object> handleFuncionarioJaVotoExeception(FuncionarioJaVotoExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.FUNCIONARIO_JA_VOTO, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(exceptionResponse);
    }
    @ExceptionHandler(NaoPodeVotarExeception.class)
    public final ResponseEntity<Object> handleNaoPodeVotarExeception(NaoPodeVotarExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.NAO_PODE_VOTAR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(exceptionResponse);
    }
    @ExceptionHandler(VotacaoEncerradaExeception.class)
    public final ResponseEntity<Object> handleVotacaoEncerradaExeception(VotacaoEncerradaExeception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.VOTACAO_ENCERRADA, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(exceptionResponse);
    }

}
