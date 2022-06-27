package model;

/**
 * class for holding important data about the running session of the application
 * Follows the Singleton pattern
 */
public class ApplicationSession {
    // region Fields
    private static ApplicationSession _session;
    private User _activeUser;
    // endregion

    // region Methods

    /**
     * gets the current running session and creates one if not previously done.
     * @return
     */
    public static ApplicationSession getSession() {
        if (_session == null) {
            _session = new ApplicationSession();
        }
        return _session;
    }

    // region Get/Set
    public void setActiveUser(User u){
        _activeUser = u;
    }
    public User getActiveUser(){
        return _activeUser;
    }
    // endregion
    // endregion
}
