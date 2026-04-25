package com.example.event.Exception;

/**
 * Thrown when a resource is already booked on the requested date
 */
public class DoubleBookingException extends RuntimeException {

    public DoubleBookingException(String message) {
        super(message);
    }
}