package org.bosch.intern.exception;

import java.io.IOException;

public class BookStoreException extends RuntimeException {
    private final String errorMessage;
    public BookStoreException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
