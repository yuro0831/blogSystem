
package com.example.blog.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
