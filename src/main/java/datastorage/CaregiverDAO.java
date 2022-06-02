package datastorage;

import model.Caregiver;
import model.Patient;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CaregiverDAO extends DAOimp<Caregiver>
{
    public CaregiverDAO(Connection conn)
    {
        super(conn);
    }

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

    @Override
    protected String getReadByIDStatementString(long key)
    {
        return String.format("SELECT * FROM caregiver WHERE cid = %d", key);
    }

    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet set) throws SQLException
    {
        Caregiver c;
        c = new Caregiver(set.getLong(0),
                set.getString(1),
                set.getString(2),
                set.getString(3),
                set.getString(4),
                set.getBoolean(5),
                set.getString(6));
        return c;
    }

    @Override
    protected String getReadAllStatementString()
    {
        return "SELECT * FROM caregiver";
    }

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

    @Override
    protected String getDeleteStatementString(long key)
    {
        return String.format("Delete FROM caregiver WHERE cid=%d", key);
    }
}
