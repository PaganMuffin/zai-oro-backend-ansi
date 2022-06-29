package com.example.ansi.model;


import com.example.ansi.model.anilist.CoverImage;
import com.example.ansi.model.anilist.Title;
import com.example.ansi.model.mapped.BaseSeriesModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "series")
public class SeriesModel extends BaseSeriesModel<SeriesModel> {

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    @Getter
    protected Set<SubtitleEntry> entries;

    public SeriesModel() {
    }

    public SeriesModel(String season, String description, int alId, int idMal, String format, int seasonYear, int episodes, CoverImage coverImage, Title title) {
        this.season = season;
        this.description = description;
        this.alId = alId;
        this.idMal = idMal;
        this.format = format;
        this.seasonYear = seasonYear;
        this.episodes = episodes;
        this.coverImage = coverImage;
        this.title = title;
    }

}
