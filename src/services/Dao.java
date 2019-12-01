package services;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T> {
    boolean doesTableExist() throws SQLException;
    void createTable() throws SQLException;
    void create(T t) throws SQLException;
    T getById(int id) throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
}
