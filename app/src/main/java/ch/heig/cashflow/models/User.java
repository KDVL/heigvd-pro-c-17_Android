/**
 * Model user
 * <p>
 * Used by Signup class
 *
 * @authors Kevin DO VALE
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
     * Constructor
     * @param firstname
     * @param name
     * @param email
     * @param password
     */
    public User(String firstname, String name, String email, String password) {
        this.firstname = firstname;
        this.lastname = name;
        this.email = email;
        this.username = email;
        this.password = password;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }
}
