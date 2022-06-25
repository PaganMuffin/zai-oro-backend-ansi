package com.example.ansi.model;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileModel {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    @Getter
    private String id;

    @Getter
    private String filename;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    public FileModel() {
    }

    public FileModel(String filename, UserModel user) {
        this.filename = filename;
        this.user = user;
    }
}
