package com.example.ansi.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    @Getter
    private String id;

    @Getter
    @Column(name = "email", unique = true, nullable = false)
    @JsonIgnore
    private String email;

    @Getter
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Column(name = "username", nullable = false)
    private String username;

    @Getter
    @Column(name = "avatar", nullable = true)
    private String avatar;

    @Getter
    @Column(name = "role")
    @ColumnDefault("user")
    private String role;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private Set<SessionModel> sessions;


    public UserModel(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = "user";

    }

    public UserModel() {}

}
