package model;

public class Caregiver extends User
{
    private long _cid;
    private String _phoneNumber;

    public Caregiver(String firstName, String surname,
                     String username, String password, boolean isAdmin, String phoneNumber)
    {
        super(firstName, surname, username, password, isAdmin);
        _phoneNumber = phoneNumber;
    }

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
