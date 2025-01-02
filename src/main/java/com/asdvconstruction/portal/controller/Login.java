package com.asdvconstruction.portal.controller;

import com.asdvconstruction.portal.model.User;
import com.asdvconstruction.portal.util.Database;
import com.asdvconstruction.portal.util.Utilities;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Michael C. Herrera
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String username;
    private String password;
    private static User user;

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
     * Get the value of user.
     *
     * @return the value of user
     */
    public static User getUser() {return user;}

    /**
     * Validate login credentials and return redirect.
     *
     * @return redirect
     */
    public String login() {

//        user = new User(username, password, "admin");
        user = new User("root", "root", "admin");

        try (Connection connection = Database.connection(user)) {
            ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).setAttribute(
                    "user", user);
            return "/portal/supplier?faces-redirect=true";
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Login", null);
            username = null;
            password = null;
            user = null;
        }

        return "";
    }

    /**
     * Logout and return redirect.
     *
     * @return redirect
     */
    public String logout() {

        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        return "/index.xhtml?faces-redirect=true";
    }
}
