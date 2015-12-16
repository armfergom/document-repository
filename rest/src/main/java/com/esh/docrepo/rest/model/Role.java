package com.esh.docrepo.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "authentication", name = "user_roles")
public class Role {

    @Id
    @Column(name = "user_role_id")
    private Integer userRoleId;
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
    private String role;

    public User getUser() {
        return user;
    }

    public void setUser(User username) {
        this.user = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role [user=" + user.getUsername() + ", role=" + role + "]";
    }

}
