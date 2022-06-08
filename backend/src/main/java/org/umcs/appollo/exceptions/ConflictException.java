package org.umcs.appollo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException() {
        super();
    }
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConflictException(String message) {
        super(message);
    }
    public ConflictException(Throwable cause) {
        super(cause);
    }
}
