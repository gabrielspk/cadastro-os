package com.github.gabrielspk.cadastro_os.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateUserException(String message) {
        super(message);
    }
}
