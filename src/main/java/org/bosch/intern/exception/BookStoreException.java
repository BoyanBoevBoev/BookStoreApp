package org.bosch.intern.exception;

public class BookStoreException extends RuntimeException {
    private final String errorMessage;

    public BookStoreException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
