package com.example.ansi.model;


import com.example.ansi.model.anilist.CoverImage;
import com.example.ansi.model.anilist.Title;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.w3c.dom.Text;

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
    @Setter
    private CoverImage coverImage;

    @Getter
    @Setter
    private String season;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String description;

    @Getter
    @Setter
    private int alId;

    @Getter
    @Setter
    private int idMal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    @Setter
    @Getter
    private Title title;

    @Setter
    @Getter
    private String type;

    @Setter
    @Getter
    private int seasonYear;

    @Setter
    @Getter
    private int episodes;

    public SeriesModel() {
    }

    public SeriesModel(String season, String description, int alId, int idMal, String type, int seasonYear, int episodes, CoverImage coverImage, Title title) {
        this.season = season;
        this.description = description;
        this.alId = alId;
        this.idMal = idMal;
        this.type = type;
        this.seasonYear = seasonYear;
        this.episodes = episodes;
        this.coverImage = coverImage;
        this.title = title;
    }

}
