package ru.kata.spring.boot_security.demo.util;

public class ResponseError {
    private String message;
    private long timestamp;

    public ResponseError(final String message, final long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }
}
