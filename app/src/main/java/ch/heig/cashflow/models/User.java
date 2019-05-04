/**
 * Model user
 *
 * Used by Signup class
 * @see ch.heig.cashflow.activites.SignupActivity
 * @see ch.heig.cashflow.network.SignupService
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */


package ch.heig.cashflow.models;

public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public User(String firstname, String name, String email, String password){
        this.firstname = firstname;
        this.lastname = name;
        this.email = email;
        this.username = email;
        this.password = password;
    }
}
