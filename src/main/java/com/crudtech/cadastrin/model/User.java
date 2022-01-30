package com.crudtech.cadastrin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User {

    @Id @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6)
    private String password;
    @Column(unique = true)
    private String username;
    @JsonProperty(defaultValue = "true", access = JsonProperty.Access.WRITE_ONLY)
    private boolean active;

    public User(Long id, String email, String name, String password, String username, boolean active) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.username = username;
        this.active = active;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnoreProperties(allowGetters = true)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isActive() == user.isActive() && getId().equals(user.getId()) && getEmail().equals(user.getEmail()) && getName().equals(user.getName()) && getPassword().equals(user.getPassword()) && getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getPassword(), getUsername(), isActive());
    }

    @Override
    public String toString() {
        return "{" +
                "\"email\":" + email +
                ", \"name\":\"" + name + "\"" +
                ", \"username\":\"" + username + "\"" +
                "}";
    }
}
