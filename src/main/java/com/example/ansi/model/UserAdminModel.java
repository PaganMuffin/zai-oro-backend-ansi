package com.example.ansi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserAdminModel  {


    @Getter
    private String id;

    @Getter
    private String email;

    @Getter
    private String username;

    @Getter
    private String avatar;

    @Getter
    private String role;

     public UserAdminModel(UserModel user) {
         this.id = user.getId();
         this.email = user.getEmail();
         this.username = user.getUsername();
         this.avatar = user.getAvatar();
         this.role = user.getRole();
     }
}
