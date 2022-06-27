package model;

/**
 * Caregivers work in a nursing home
 */
public class Caregiver extends User
{
    // region Fields
    private long _cid;
    private String _phoneNumber;
    // endregion

    // region Constructor

    /**
     * Constructor for a caregiver
     * @param firstName The first name for the {@link Person}
     * @param surname The surname for the {@link Person}
     * @param username The username for the {@link User}
     * @param password The password for the {@link User}
     * @param isAdmin Has this {@link User} administrator rights?
     * @param phoneNumber The phone number for this Caregiver
     */
    public Caregiver(String firstName, String surname,
                     String username, String password, boolean isAdmin, String phoneNumber)
    {
        super(firstName, surname, username, password, isAdmin);
        _phoneNumber = phoneNumber;
    }

    /**
     * Constructor for a caregiver
     * @param cid the caregiver-ID
     * @param firstName The first name for the {@link Person}
     * @param surname The surname for the {@link Person}
     * @param username The username for the {@link User}
     * @param password The password for the {@link User}
     * @param isAdmin Has this {@link User} administrator rights?
     * @param phoneNumber The phone number for this Caregiver
     */
    public Caregiver(long cid, String firstName, String surname,
                     String username, String password, boolean isAdmin, String phoneNumber)
    {
        super(firstName, surname, username, password, isAdmin);
        _cid = cid;
        _phoneNumber = phoneNumber;
    }

    // region Get/Set

    public long getCid() { return _cid; }

    public String getPhoneNumber() { return _phoneNumber; }

    public void setCid(long cid) { _cid = cid; }

    public void setPhoneNumber(String phoneNumber) { _phoneNumber = phoneNumber; }

    // endregion
}
