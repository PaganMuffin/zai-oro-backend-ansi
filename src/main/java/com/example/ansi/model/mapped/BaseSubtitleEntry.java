package com.example.ansi.model.mapped;

import com.example.ansi.model.SeriesModel;
import com.example.ansi.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseSubtitleEntry<T extends BaseSubtitleEntry> {


    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    @Getter
    protected String id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    protected UserModel user;


    @Getter
    protected Long createdAt;

    @Getter
    protected Long updatedAt;

    @Getter
    protected String author;

    @Getter
    protected Integer episode;

    @Getter
    protected String Description;

    @Getter
    protected String filename;

    public BaseSubtitleEntry() {
    }

}
