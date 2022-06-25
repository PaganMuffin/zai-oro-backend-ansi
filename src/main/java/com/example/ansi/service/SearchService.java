package com.example.ansi.service;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import net.minidev.json.JSONObject;

import java.util.List;

public interface SearchService {
    public JSONObject search(String q, Integer p, Integer limit);
}
