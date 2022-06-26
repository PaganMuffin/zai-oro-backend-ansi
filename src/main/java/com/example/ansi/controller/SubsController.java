package com.example.ansi.controller;

import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
public class SubsController {

    @Autowired
    private SearchRepository searchRepository;

    @GetMapping("/view/{id}")
    public SearchSubtitleEntryModel testGet(@PathVariable String id) {
        return searchRepository.findById(id);

    }
}
