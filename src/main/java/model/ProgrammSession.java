package model;

import datastorage.ConnectionBuilder;

import java.sql.Connection;

public class ProgrammSession
{
    private static ProgrammSession _session;
    private User _activeUser;

    public static ProgrammSession getSession() {
        if (_session == null) {
            _session = new ProgrammSession();
        }
        return _session;
    }

    public void setActiveUser(User u){
        _activeUser = u;
    }
    public User getActiveUser(){
        return _activeUser;
    }
}
