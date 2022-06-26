package com.example.ansi.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class EditRequestModel {
    @Getter
    @Setter
    private Integer episode;

    @Getter
    @Setter
    private String desc;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    private String fileChange;

    public EditRequestModel() {
    }

}
