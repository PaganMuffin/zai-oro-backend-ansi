package com.example.ansi.controller;

import com.example.ansi.model.AddRequestModel;
import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.service.SearchService;
import com.example.ansi.service.SeriesService;
import com.example.ansi.utills.AniList;
import com.example.ansi.utills.Utills;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SearchService searchService;

    @GetMapping("/searchAniList")
    public String searchAniList(@RequestParam String q, @RequestParam(required = false) Integer p, HttpServletRequest request, HttpServletResponse response) throws UnirestException {
        p = p == null ? 1 : p;
        return AniList.searchByTitle(q, p);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDB(@RequestParam String q,
                                      @RequestParam(required = false) Integer p,
                                      @RequestParam(required = false) Integer limit,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws UnirestException {
        p = p == null ? 1 : p;
        limit = limit == null ? 10 : limit;
        JSONObject result = searchService.search(q, p, limit);
        return Utills.buildResponse(result, 200, "");
    }


}
