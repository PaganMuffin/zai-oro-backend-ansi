package com.example.ansi.controller;

import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost"}, allowedHeaders = "*", allowCredentials = "true")
public class CommentsController {

    @Autowired
    private SearchRepository searchRepository;

    //id is the id of the entry
    @GetMapping("/comments/{id}")
    public String getComments(@PathVariable String id) {
        return null;

    }

    //id is the id of the entry
    @PostMapping("/comment/{id}")
    public String postComment(@PathVariable String id) {
        return null;

    }
}
