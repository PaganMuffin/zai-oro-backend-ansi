package com.example.ansi.model;


import com.example.ansi.model.anilist.CoverImage;
import com.example.ansi.model.anilist.Title;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "series")
public class SeriesModel {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    @Getter
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_id")
    @Getter
    private CoverImage coverImage;

    @Getter
    private String season;

    @Getter
    private String description;

    @Getter
    private int alId;

    @Getter
    private int idMal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    private Title title;

    @Getter
    private String type;

    @Getter
    private int seasonYear;

    @Getter
    private int episodes;

    public SeriesModel() {
    }

    public SeriesModel(String season, String description, int alId, int idMal, String type, int seasonYear, int episodes) {
        this.season = season;
        this.description = description;
        this.alId = alId;
        this.idMal = idMal;
        this.type = type;
        this.seasonYear = seasonYear;
        this.episodes = episodes;
    }

}
