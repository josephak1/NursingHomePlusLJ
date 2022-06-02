package model;

public abstract class User extends Person
{
    private String _username;
    private String _password;
    private boolean _isAdmin;

    public User(String firstName, String surname,
                String username, String password, boolean isAdmin)
    {
        super(firstName, surname);
        _username = username;
        _password = password;
        _isAdmin = isAdmin;
    }

    // region Get/Set

    public String getUsername() { return _username; }

    public String getPassword() { return _password; }

    public boolean getIsAdmin() { return _isAdmin; }

    public void setUsername(String username){ _username = username; }

    public void setPassword(String password){ _password = password; }

    public void setIsAdmin(boolean isAdmin){ _isAdmin = isAdmin; }

    // endregion
}
