package com.example.ansi.model.search;


import com.example.ansi.model.SeriesModel;
import com.example.ansi.model.mapped.BaseSubtitleEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subtitles")
public class SearchSubtitleEntryModel extends BaseSubtitleEntry<SearchSubtitleEntryModel> {

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "series_id", nullable = false)
    private SearchSeriesModel series;

    @Getter
    private String id;
    @Getter
    private Long createdAt;
    @Getter
    private Long updatedAt;
    @Getter
    private Integer episode;
    @Getter
    private String Description;
    @Getter
    private String filename;

    public SearchSubtitleEntryModel() {
    }


}
