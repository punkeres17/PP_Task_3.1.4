package ru.kata.spring.boot_security.demo.util;

import java.io.Serial;

public class UserNotAddedAndNotUpdatedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -352979249336866929L;

    public UserNotAddedAndNotUpdatedException(final String message) {
        super(message);
    }
}
