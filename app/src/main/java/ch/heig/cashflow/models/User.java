/**
 * Model user
 * <i>Someone that uses.</i>
 *
 * <p>
 * Defines a simple user with a firstname, a lastname, a username, an email and a password.
 * Used by {@code SignupActivity} class.
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.activites.SignupActivity
 * @see ch.heig.cashflow.network.services.SignupService
 */

package ch.heig.cashflow.models;

public class User {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

    /**
     * The User constructor
     *
     * @param firstname The user firstname
     * @param lastname  The user lastname
     * @param email     The user email
     * @param password  The user password
     */
    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = email;
        this.password = password;
    }

    /**
     * Get the firstname of the user
     *
     * @return String The user firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Get the lastname of the user
     *
     * @return String The user lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Get the username of the user
     *
     * @return String The user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the email of the user
     *
     * @return String The user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the password of the user
     *
     * @return String The user password
     */
    public String getPassword() {
        return password;
    }
}
