package ru.tedusar.Exceptions;

public class BlankLineError extends RuntimeException {
    public BlankLineError(String message) {
        super(message);
    }
}
