package datastorage;

import model.Patient;
import utils.DateConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class PatientDAO extends DAOimp<Patient> {

    // region Constructor

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public PatientDAO(Connection conn) {
        super(conn);
    }

    //endregion

    // region Methods

    // region SQL Statements
    /**
     * generates a <code>INSERT INTO</code>-Statement for a given patient
     * @param patient for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Patient patient) {
        return String.format("INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber) VALUES ('%s', '%s', '%s', '%s', '%s')",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM patient WHERE pid = %d", key);
    }

    /**
     * generates a <code>SELECT</code>-Statement for all patients.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM patient";
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given patient
     * @param patient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Patient patient) {
        return String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                        "roomnumber = '%s' WHERE pid = %d", patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), patient.getPid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM patient WHERE pid=%d", key);
    }
    public void archiveById(long key, String date) throws SQLException {

        Statement st = conn.createStatement();
        st.executeUpdate(String.format("UPDATE patient Set Archivdatum = '%s' WHERE pid=%d", date, key));
    }

    public List<Patient> readActive() throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        Patient object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM patient WHERE Archivdatum IS NULL");
        list = getListFromResultSet(result);
        return list;
    }
    // endregion

    // region Getter from ResultSets
    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     * @param result ResultSet with a single row. Columns will be mapped to a patient-object.
     * @return patient with the data from the resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        Patient p;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        String archString = result.getString(7);

        LocalDate archived = archString == null ?
                null :
                DateConverter.convertStringToLocalDate(result.getString(7));
        p = new Patient(result.getInt(1),
                result.getString(2),
                result.getString(3),
                date,
                result.getString(5),
                result.getString(6),
                archived);

        return p;
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to patient-object.
     * @return ArrayList with patients from the resultSet.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        Patient p;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            String archString = result.getString(7);

            LocalDate archived = archString == null ?
                    null :
                    DateConverter.convertStringToLocalDate(result.getString(7));
            p = new Patient(result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    date,
                    result.getString(5),
                    result.getString(6),
                    archived);
            list.add(p);
        }
        return list;
    }
    //endregion

    //endregion
}
