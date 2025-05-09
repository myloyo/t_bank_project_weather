package ru.tedusar.services;

import java.sql.*;
import ru.tedusar.entity.UserClass;
import ru.tedusar.repositories.UserRepository;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserClass authenticate(String email, String password) throws SQLException {
        UserClass user = repository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void register(UserClass user) throws SQLException {
        repository.save(user);
    }
}