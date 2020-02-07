package org.cyranno.springserverless.sum;

import org.cyranno.springserverless.SpringServerLessException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class SumException extends SpringServerLessException {
    private static final long serialVersionUID = -8811448964405456954L;

    public SumException(final String message) {
        super(BAD_REQUEST.value(), message, null);
    }
}
