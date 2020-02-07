package org.cyranno.springserverless;

public abstract class SpringServerLessException extends RuntimeException {
    private static final long serialVersionUID = 7249177844846323679L;
    private final int status;

    public SpringServerLessException(final int status, final String message, final Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
