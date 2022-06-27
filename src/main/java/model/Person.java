package model;

/**
 * representation of a Person
 */
public abstract class Person {
    // region Fields
    private String firstName;
    private String surname;
    // endregion

    // region Constructor

    /**
     * constructor for a person
     * @param firstName
     * @param surname
     */
    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }
    // endregion

    // region Get/Set
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    // endregion
}
