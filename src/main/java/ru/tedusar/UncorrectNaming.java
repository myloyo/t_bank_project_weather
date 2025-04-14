package ru.tedusar;

public class UncorrectNaming extends IllegalArgumentException {
    public UncorrectNaming(String message) {
        super(message);
    }
}
