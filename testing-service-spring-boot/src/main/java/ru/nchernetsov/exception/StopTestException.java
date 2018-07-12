package ru.nchernetsov.exception;

public class StopTestException extends RuntimeException {

    public StopTestException() {
    }

    public StopTestException(String message) {
        super(message);
    }
}
