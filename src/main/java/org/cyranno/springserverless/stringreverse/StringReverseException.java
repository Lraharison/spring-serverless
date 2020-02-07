package org.cyranno.springserverless.stringreverse;

import org.cyranno.springserverless.SpringServerLessException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class StringReverseException extends SpringServerLessException {
    private static final long serialVersionUID = 1329008703097337224L;

    public StringReverseException(final String message) {
        super(BAD_REQUEST.value(), message, null);
    }
}
