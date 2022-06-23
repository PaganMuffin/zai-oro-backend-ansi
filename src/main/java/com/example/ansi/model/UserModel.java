package com.example.ansi.model;

import javax.persistence.*;

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

    private String email;

    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Column(name = "username", nullable = false)
    private String username;

    @OneToMany(mappedBy="user")
    private Set<SessionModel> sessions;


    public UserModel(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UserModel() {}

}
