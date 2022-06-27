package datastorage;

import model.Caregiver;
import model.Patient;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class CaregiverDAO extends DAOimp<Caregiver>
{
    // region Constructor

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public CaregiverDAO(Connection conn)
    {
        super(conn);
    }
    // endregion

    // region Methods
    // region SQL Statements
    /**
     * generates a <code>INSERT INTO</code>-Statement for a given caregiver
     * @param caregiver for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Caregiver caregiver)
    {
        return String.format("INSERT INTO caregiver (firstname, surname, username, password, isAdmin, phoneNumber) VALUES ('%s', '%s', '%s', '%s', %d, '%s')",
                caregiver.getFirstName(),
                caregiver.getSurname(),
                caregiver.getUsername(),
                caregiver.getPassword(),
                caregiver.getIsAdmin() ? 1 : 0,
                caregiver.getPhoneNumber());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key)
    {
        return String.format("SELECT * FROM caregiver WHERE cid = %d", key);
    }

    /**
     * generates a <code>SELECT</code>-Statement for all caregivers.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString()
    {
        return "SELECT * FROM caregiver";
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given caregiver
     * @param caregiver for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Caregiver caregiver)
    {
        return String.format("UPDATE caregiver SET firstname = '%s', surname = '%s', username = '%s', password = '%s', " +
                        "isAdmin = '%d', phoneNumber = '%s' WHERE cid = %s",
                caregiver.getFirstName(),
                caregiver.getSurname(),
                caregiver.getUsername(),
                caregiver.getPassword(),
                caregiver.getIsAdmin() ? 1 : 0,
                caregiver.getPhoneNumber(),
                caregiver.getCid()
        );
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key)
    {
        return String.format("Delete FROM caregiver WHERE cid=%d", key);
    }
    // endregion

    // region Getter from ResultSets
    /**
     * maps a <code>ResultSet</code> to a <code>Caregiver</code>
     * @param set ResultSet with a single row. Columns will be mapped to a caregiver-object.
     * @return caregiver with the data from the resultSet.
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet set) throws SQLException
    {
        Caregiver c;
        c = new Caregiver(set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getString(4),
                set.getString(5),
                set.getBoolean(6),
                set.getString(7));
        return c;
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Caregiver-List</code>
     * @param set ResultSet with a multiple rows. Data will be mapped to caregiver-object.
     * @return ArrayList with caregivers from the resultSet.
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet set) throws SQLException
    {
        ArrayList<Caregiver> list = new ArrayList();
        Caregiver c;
        while (set.next()) {
            c = new Caregiver(set.getLong(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    set.getBoolean(6),
                    set.getString(7));
            list.add(c);
        }
        return list;
    }

    // endregion
    // endregion
}
