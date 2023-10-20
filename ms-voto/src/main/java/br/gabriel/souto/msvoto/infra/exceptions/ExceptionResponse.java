package br.gabriel.souto.msvoto.infra.exceptions;

import br.gabriel.souto.msvoto.domain.enums.ErrorCodes;
import lombok.Data;

@Data
public class ExceptionResponse {
    private final String code;
    private final String message;

    public ExceptionResponse(final ErrorCodes errorCode, String details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }


}
