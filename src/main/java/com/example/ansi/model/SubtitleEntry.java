package com.example.ansi.model;

import com.example.ansi.model.mapped.BaseSubtitleEntry;
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
public class SubtitleEntry extends BaseSubtitleEntry<SubtitleEntry> {


    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "series_id", nullable = false)
    @JsonIgnore
    private SeriesModel series;

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
