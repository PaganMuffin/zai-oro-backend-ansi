package com.example.ansi.controller;

import com.example.ansi.model.AddRequestModel;
import com.example.ansi.model.EditRequestModel;
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
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;
    
    @PostMapping("/add")
    public ResponseEntity<?> add(AddRequestModel addRequestModel, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws UnirestException {
        return seriesService.add(addRequestModel, file, request, response);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(EditRequestModel editRequestModel, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws UnirestException {
        return seriesService.edit(id, editRequestModel, file, request, response);
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<?> getSeries(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws UnirestException {
        return seriesService.getSeries(id, request, response);
    }


}
