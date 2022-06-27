package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract class for a DAO-Object
 * Implements the Interface {@link DAO}
 * @param <T>
 */
public abstract class DAOimp<T> implements DAO<T>{
    // region Fields
    protected Connection conn;
    // endregion

    // region Constructor

    /**
     * Constructor for a {@link DAOimp} object
     * @param conn the connection to the database
     */
    public DAOimp(Connection conn) {
        this.conn = conn;
    }
    // endregion

    // region Methods

    /**
     * creates object on the database
     * @param t
     * @throws SQLException
     */
    @Override
    public void create(T t) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getCreateStatementString(t));
    }

    /**
     * reads an object by the id from the database
     * @param key the id to search
     * @return the object or null if nothing was found
     * @throws SQLException
     */
    @Override
    public T read(long key) throws SQLException {
        T object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadByIDStatementString(key));
        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }
        return object;
    }

    /**
     * reads all entries from the database of the fitting type
     * @return List containing all objects on the database
     * @throws SQLException
     */
    @Override
    public List<T> readAll() throws SQLException {
        ArrayList<T> list = new ArrayList<T>();
        T object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllStatementString());
        list = getListFromResultSet(result);
        return list;
    }

    /**
     * updates the object on th database
     * @param t the object to update
     * @throws SQLException
     */
    @Override
    public void update(T t) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getUpdateStatementString(t));
    }

    /**
     * deletes a row on the database with the fitting id
     * @param key the id
     * @throws SQLException
     */
    @Override
    public void deleteById(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(getDeleteStatementString(key));
    }

    // region abstract Methods

    protected abstract String getCreateStatementString(T t);

    protected abstract String getReadByIDStatementString(long key);

    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getReadAllStatementString();

    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    protected abstract String getUpdateStatementString(T t);

    protected abstract String getDeleteStatementString(long key);
    // endregion
    // endregion
}
