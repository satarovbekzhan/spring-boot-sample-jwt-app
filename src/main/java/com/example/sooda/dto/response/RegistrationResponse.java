package com.example.sooda.dto.response;

import com.example.sooda.entity.Role;

import java.util.HashSet;
import java.util.Set;

public class RegistrationResponse {

    private final String username;
    private final String password;
    private final Set<String> roles;

    public RegistrationResponse(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
        for (Role role : roles) {
            this.roles.add(role.toString());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
