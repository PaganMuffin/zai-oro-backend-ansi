package com.example.ansi.controller;

import com.example.ansi.utills.AniList;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeriesController {
    @GetMapping("/search")
    public String searchAniList(@RequestParam String q, @RequestParam(required = false) Integer p) throws UnirestException {
        p = p == null ? 1 : p;
        return AniList.searchByTitle(q,p);
    }
}
