package com.example.ansi.model.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchResponseModel {

    @Getter
    @Setter
    private List<SearchSubtitleEntryModel> results;
}
