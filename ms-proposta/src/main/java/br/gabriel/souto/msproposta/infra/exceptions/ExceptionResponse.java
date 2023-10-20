package br.gabriel.souto.msproposta.infra.exceptions;

import br.gabriel.souto.msproposta.domain.enums.ErrorCodes;
import lombok.Data;

@Data
public class ExceptionResponse {
    private final String code;
    private final String message;

    public ExceptionResponse(final ErrorCodes errorCode, String message) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }

}
