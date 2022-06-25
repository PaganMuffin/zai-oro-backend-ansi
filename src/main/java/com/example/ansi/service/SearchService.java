package com.example.ansi.service;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;

import java.util.List;

public interface SearchService {
    public List<SearchSubtitleEntryModel> search(String q, Integer p);
}
