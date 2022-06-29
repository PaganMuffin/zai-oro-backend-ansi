package com.example.ansi.model;

import lombok.Getter;
import lombok.Setter;

public class UserAdminUpdateModel {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String role;

    public UserAdminUpdateModel() {
    }
}
