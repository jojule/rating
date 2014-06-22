package org.vaadin.rating.service;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import java.io.Serializable;

@SessionScoped
public class User implements Serializable {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {

        if (email == null) return false;

        // TODO implement access control system
        return email.startsWith("admin");
    }
}
