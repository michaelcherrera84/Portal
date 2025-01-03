package com.asdvconstruction.portal.model;

/**
 * @author Michael C. Herrera
 */
@SuppressWarnings("unused")
public class User {

    private String username;
    private String password;

    /**
     * Variable to determine the access level of the User.
     */
    private String role;

    /**
     * Construct a new User.
     *
     * @param username username of User
     * @param password password of User
     * @param role     role of User
     */
    public User(String username, String password, String role) {

        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Get the value of username.
     *
     * @return the value of username
     */
    public String getUsername() {return username;}

    /**
     * Set the value of username.
     *
     * @param username new value of username
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Get the value of password.
     *
     * @return the value of password
     */
    public String getPassword() {return password;}

    /**
     * Set the value of password.
     *
     * @param password new value of password
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Get the value of role.
     *
     * @return the value of role
     */
    public String getRole() {return role;}

    /**
     * Set the value of role.
     *
     * @param role new value of role
     */
    public void setRole(String role) {this.role = role;}
}
