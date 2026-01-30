package com.amdox.taskmanagement.Entity;

import com.amdox.taskmanagement.Enum.Role;
import jakarta.persistence.*;

@Entity
@Table(name="user_auth")
public class UserAuthenticate {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String userName;

    @Column(unique=true, nullable=false)
    private String userOfficialEmail;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    public UserAuthenticate() {
    }

    public UserAuthenticate(String userName, String userOfficialEmail, String password, Role role) {
        this.userName = userName;
        this.userOfficialEmail = userOfficialEmail;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserOfficialEmail() {
        return userOfficialEmail;
    }

    public void setUserOfficialEmail(String userOfficialEmail) {
        this.userOfficialEmail = userOfficialEmail;
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
