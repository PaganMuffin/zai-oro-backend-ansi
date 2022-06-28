package com.example.ansi.model;

import com.example.ansi.utills.Utills;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name ="comments")
public class CommentModel {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    @Getter
    private String id;

    @Getter
    @Column(columnDefinition = "TEXT")
    private String content;

    @Getter
    private Long createdAt;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "entry_id", nullable = false)
    @JsonIgnore
    private SubtitleEntry entrie;


    public CommentModel() {

    }

    public CommentModel(String content, UserModel user, SubtitleEntry entrie) {
        this.content = content;
        this.user = user;
        this.entrie = entrie;
        this.createdAt = Utills.getUnixTime();
    }


}
