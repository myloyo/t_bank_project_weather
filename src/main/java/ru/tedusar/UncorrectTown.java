package ru.tedusar;

public class UncorrectTown extends IllegalArgumentException {
    public UncorrectTown(String message) {
        super(message);
    }
}
