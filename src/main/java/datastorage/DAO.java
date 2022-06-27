package datastorage;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for DAO methods
 * @param <T>
 */
public interface DAO<T> {
    void create(T t) throws SQLException;

    T read(long key) throws SQLException;

    List<T> readAll() throws SQLException;

    void update(T t) throws SQLException;

    void deleteById(long key) throws SQLException;
}
