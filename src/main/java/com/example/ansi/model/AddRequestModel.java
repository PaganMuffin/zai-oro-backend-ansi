package com.example.ansi.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class AddRequestModel {

    @SerializedName("show_id")
    @Getter
    private Integer showId;

    @SerializedName("ep")
    @Getter
    private Integer episode;

    @SerializedName("desc")
    @Getter
    private String desc;

    @SerializedName("author")
    @Getter
    private String author;

    public AddRequestModel(Integer showId, Integer episode, String desc, String author) {
        this.showId = showId;
        this.episode = episode;
        this.desc = desc;
        this.author = author;
    }


}
