package org.cyranno.springserverless;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String message;
    private final Throwable cause;
    private final int status;

    public ErrorResponse(final SpringServerLessException exception) {
        this.message = exception.getMessage();
        this.cause = exception.getCause();
        this.status = exception.getStatus();
    }
}
