package com.example.ansi.model;

import com.example.ansi.utills.Utills;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "subtitles")
public class SubtitleEntry {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    @Getter
    private String id;

    @Getter
    private Long createdAt;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    @JsonIgnore
    private SeriesModel series;

    @Getter
    private Long updatedAt;

    @Getter
    private String author;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserModel user;

    @Getter
    private Integer episode;

    @Getter
    private String Description;

    @Getter
    private String filename;


    public SubtitleEntry() {

    }

    public SubtitleEntry(AddRequestModel addRequestModel, String filename, UserModel user, SeriesModel seriesInDB) {
        this.user = user;
        this.episode = addRequestModel.getEpisode();
        this.Description = addRequestModel.getDesc();
        this.filename = filename;
        this.author = addRequestModel.getAuthor();
        this.createdAt = Utills.getUnixTime();
        this.updatedAt = Utills.getUnixTime();
        this.series = seriesInDB;

    }

}
