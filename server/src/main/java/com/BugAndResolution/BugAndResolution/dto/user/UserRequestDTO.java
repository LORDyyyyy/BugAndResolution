package com.BugAndResolution.BugAndResolution.dto.user;

import com.BugAndResolution.BugAndResolution.model.enums.Role;
import jakarta.validation.constraints.NotEmpty;

public class UserRequestDTO {
    @NotEmpty(message = "Name cannot be null or empty")
    private String name;
    @NotEmpty(message = "Email cannot be null or empty")
    private String email;
    @NotEmpty(message = "Password cannot be null or empty")
    private String password;
    @NotEmpty(message = "Role cannot be null or empty")
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
