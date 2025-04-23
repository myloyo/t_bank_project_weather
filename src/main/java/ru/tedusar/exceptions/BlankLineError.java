package ru.tedusar.exceptions;

public class BlankLineError extends RuntimeException {
    public BlankLineError(String message) {
        super(message);
    }
}
