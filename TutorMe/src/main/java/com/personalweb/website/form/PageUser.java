package com.personalweb.website.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Size;


public class PageUser {

    @NotEmpty(message="{error.emailRequired}")
    @Email(message="{error.emailRequired}")
    String email;

    @Size(min = 2, max = 20, message="The length must be between {min} and {max}")
    String firstName;

    @Size(min = 2, max = 20, message="The length must be between {min} and {max}")
    String lastName;

    @NotEmpty(message="{error.passwordRequired}")
    @Size(min = 6, max = 25, message="The length must be between {min} and {max}")
    String password;

    @NotEmpty
    @Size(min = 3, max = 20, message="{error.usernameRequired}")
    String username;

    Long userID;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
