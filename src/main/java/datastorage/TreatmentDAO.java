package datastorage;

import model.Treatment;
import utils.DateConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific treatment-SQL-queries.
 */
public class TreatmentDAO extends DAOimp<Treatment> {

    // region Constructor
    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public TreatmentDAO(Connection conn) {
        super(conn);
    }
    // endregion

    // region Methods

    // region Generate SQL Strings
    /**
     * generates a <code>INSERT INTO</code>-Statement for a given treatment
     * @param treatment for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Treatment treatment) {
        return String.format("INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks, cid) VALUES " +
                "(%d, '%s', '%s', '%s', '%s', '%s', %d)", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks(), treatment.getCid());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE tid = %d", key);
    }

    /**
     * generates a <code>SELECT</code>-Statement for all treatments.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM treatment";
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given treatment
     * @param treatment for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Treatment treatment) {
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s'," +
                        "description = '%s', remarks = '%s', cid = %d WHERE tid = %d", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(), treatment.getCid(),
                treatment.getTid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM treatment WHERE tid= %d", key);
    }

    /**
     * generates a <code>select</code>-Statement for a given patient-ID
     * @param pid for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    private String getReadAllTreatmentsOfOnePatientByPid(long pid){
        return String.format("SELECT * FROM treatment WHERE pid = %d", pid);
    }
    /**
     * generates a <code>select</code>-Statement for a given caregiver-ID
     * @param cid for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    private String getReadAllTreatmentsOfOneCaregiverByCid(long cid){
        return String.format("SELECT * FROM treatment WHERE cid = %d", cid);
    }
    /**
     * generates a <code>select</code>-Statement for a given patient-ID and caregiver-ID
     * @param pid for which a specific SELECT is to be created
     * @param cid for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    private String getReadAllTreatmentsByPidAndCid(long pid, long cid){
        return String.format("SELECT * FROM treatment WHERE pid = %d AND cid = %d", pid, cid);
    }
    // endregion

    // region Execute SQL-Statement
    /**
     * maps a <code>long</code> to a <code>Treatment-List</code>
     * @param pid The patient-ID
     * @return ArrayList with treatments matching the given Patient-ID
     * @throws SQLException
     */
    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {

        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOnePatientByPid(pid));
        return getListFromResultSet(result);
    }

    /**
     * maps a <code>long</code> to a <code>Treatment-List</code>
     * @param cid The caregiver-ID
     * @return ArrayList with treatments matching the given Caregiver-ID
     * @throws SQLException
     */
    public List<Treatment> readTreatmentsByCid(long cid) throws SQLException {

        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOneCaregiverByCid(cid));
        return getListFromResultSet(result);
    }

    /**
     * maps two <code>longs</code> to a <code>Treatment-List</code>
     * @param pid The patient-ID
     * @param cid The caregiver-ID
     * @return ArrayList with treatments matching the given Patient-ID
     * @throws SQLException
     */
    public List<Treatment> readTreatmentsByPidAndCid(long pid, long cid) throws SQLException {

        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsByPidAndCid(pid, cid));
        return getListFromResultSet(result);
    }

    /**
     * deletes a treatment from the DB by a Patient-ID
     * @param key
     * @throws SQLException
     */
    public void deleteByPid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM treatment WHERE pid= %d", key));
    }

    /**
     * deletes a treatment from the DB by a Caregiver-ID
     * @param key
     * @throws SQLException
     */
    public void deleteByCid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM treatment WHERE cid= %d", key));
    }

    // endregion

    // region Getter from ResultSets
    /**
     * maps a <code>ResultSet</code> to a <code>Treatment</code>
     * @param result ResultSet with a single row. Columns will be mapped to a treatment-object.
     * @return treatment with the data from the resultSet.
     */
    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1),
                result.getLong(2),
                result.getLong(8),
                date, begin, end,
                result.getString(6),
                result.getString(7));
        return m;
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Treatment-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to treatment-object.
     * @return ArrayList with treatments from the resultSet.
     */
    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<>();
        Treatment t;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            t = new Treatment(result.getLong(1),
                    result.getLong(2),
                    result.getLong(8),
                    date, begin, end,
                    result.getString(6),
                    result.getString(7));
            list.add(t);
        }
        return list;
    }

    // endregion
    // endregion
}