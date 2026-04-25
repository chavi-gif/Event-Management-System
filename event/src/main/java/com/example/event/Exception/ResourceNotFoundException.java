package com.example.event.Exception;

/**
 * Thrown when a requested resource or event is not found in the database
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}