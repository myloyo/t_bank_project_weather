package ru.tedusar;

public class CustomException extends IllegalArgumentException {
    public CustomException(String message) {
        super(message);
    }
}
