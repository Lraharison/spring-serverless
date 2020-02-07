package org.cyranno.springserverless;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class UnexpectedErrorException extends SpringServerLessException {
    private static final long serialVersionUID = 1370984502710801945L;

    public UnexpectedErrorException(final String message, final Throwable cause) {
        super(INTERNAL_SERVER_ERROR.value(), message, cause);
    }
}
