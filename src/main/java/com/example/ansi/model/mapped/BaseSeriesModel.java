package com.example.ansi.model.mapped;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.anilist.CoverImage;
import com.example.ansi.model.anilist.Title;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@MappedSuperclass
public abstract class BaseSeriesModel<T extends BaseSeriesModel> {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, insertable = false)
    @Getter
    protected String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_id")
    @Getter
    @Setter
    protected CoverImage coverImage;

    @Getter
    @Setter
    protected String season;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    protected String description;

    @Getter
    @Setter
    protected int alId;

    @Getter
    @Setter
    protected int idMal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    @Setter
    @Getter
    protected Title title;

    @Setter
    @Getter
    protected String format;

    @Setter
    @Getter
    protected int seasonYear;

    @Setter
    @Getter
    protected int episodes;


    public BaseSeriesModel() {
    }
}
