package ru.tedusar.repositories;

import ru.tedusar.classes.UserClass;
import java.sql.SQLException;

public interface UserRepository {
    UserClass findByEmail(String email) throws SQLException;
    void save(UserClass user) throws SQLException;
}