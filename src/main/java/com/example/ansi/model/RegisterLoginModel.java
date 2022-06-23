package com.example.ansi.model;

import lombok.Getter;

public class RegisterLoginModel {

    @Getter
    private String username = null;

    @Getter
    private String email = null;

    @Getter
    private String password = null;


    public RegisterLoginModel(String username, String email, String password) {
        this.email = email;
        this.password = password;
        this.username = username;

    }
}
