package datastorage;

/**
 * Factory class for objects derived from the {@link DAOimp} class.
 * Follows the Singleton Pattern
 */
public class DAOFactory {
    // region Fields
    private static DAOFactory instance;
    // endregion

    // region Methods

    /**
     * gets the {@link DAOFactory} singleton object.
     * @return
     */
    public static DAOFactory getDAOFactory() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    /**
     * creates a new {@link TreatmentDAO} object
     * @return the new {@link TreatmentDAO}
     */
    public TreatmentDAO createTreatmentDAO() {
        return new TreatmentDAO(ConnectionBuilder.getConnection());
    }

    /**
     * creates a new {@link PatientDAO} object
     * @return the new {@link PatientDAO}
     */
    public PatientDAO createPatientDAO() {
        return new PatientDAO(ConnectionBuilder.getConnection());
    }

    /**
     * creates a new {@link CaregiverDAO} object
     * @return the new {@link CaregiverDAO}
     */
    public CaregiverDAO createCaregiverDAO() {
        return new CaregiverDAO(ConnectionBuilder.getConnection());
    }
    // endregion
}
