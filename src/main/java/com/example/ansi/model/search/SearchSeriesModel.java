package com.example.ansi.model.search;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.anilist.CoverImage;
import com.example.ansi.model.anilist.Title;
import com.example.ansi.model.mapped.BaseSeriesModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "series")
public class SearchSeriesModel extends BaseSeriesModel<SearchSeriesModel> {

    @Getter
    private String id;

    @OneToOne
    @JoinColumn(name = "cover_id")
    @Getter
    @Setter
    private CoverImage coverImage;

    @OneToOne
    @JoinColumn(name = "title_id")
    @Setter
    @Getter
    private Title title;


    @Transient
    @JsonIgnore
    private String season;
    @Transient
    @JsonIgnore
    private String description;
    @Transient
    @JsonIgnore
    private int alId;
    @Transient
    @JsonIgnore
    private int idMal;
    @Transient
    @JsonIgnore
    private String type;
    @Transient
    @JsonIgnore
    private int seasonYear;
    @Transient
    @JsonIgnore
    private int episodes;


    public SearchSeriesModel() {
    }
}
