package br.gabriel.souto.msproposta.infra.exceptions;

import br.gabriel.souto.msproposta.domain.enums.ErrorCodes;
import lombok.Data;

import java.util.List;

@Data
public class ExceptionResponse {
    private final String code;
    private final String message;
    public ExceptionResponse(final ErrorCodes errorCode, String details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public ExceptionResponse(ErrorCodes errorCode, List<String> details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
