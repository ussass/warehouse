package ru.trofimov.warehouse.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    protected String login;

    protected String password;

    @Transient
    protected Set<Role> roles;

    @Transient
    protected Set<SimpleGrantedAuthority> authorities;

    @Column(name = "roles")
    protected String stringRoles;

    public User() {
        roles = new HashSet<>();
        authorities = new HashSet<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getStringRoles() {
        return stringRoles;
    }

    public void setStringRoles(String stringRoles) {
        this.stringRoles = stringRoles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }


    public String setRolesToStringRoles() {
        if (roles.size() == 0) {
            stringRoles = "";
        } else {
            StringBuilder builder = new StringBuilder();
            roles.forEach(role -> builder.append(role.name()).append(","));
            stringRoles = builder.toString().substring(0, builder.toString().length() - 1);
        }
        return stringRoles;
    }

    public void addRolesFromString() {
        Arrays.stream(stringRoles.split(","))
                .forEach(s -> {
                    if (s.length() > 2) addRole(Role.valueOf(s));
                });
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", authorities=" + authorities +
                ", stringRoles='" + stringRoles + '\'' +
                '}';
    }
}
