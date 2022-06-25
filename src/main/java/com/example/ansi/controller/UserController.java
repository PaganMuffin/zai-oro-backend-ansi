package com.example.ansi.controller;

import com.example.ansi.model.SeriesModel;
import com.example.ansi.model.UserModel;
import com.example.ansi.model.anilist.Response;
import com.example.ansi.model.search.SearchSeriesModel;
import com.example.ansi.repository.SearchRepository;
import com.example.ansi.repository.SearchSeriesModelRepository;
import com.example.ansi.repository.UserRepository;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private SearchSeriesModelRepository searchSeriesModelRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> testGet(@PathVariable String id) {

        UserModel user = userRepository.findById(id);
        List<SearchSeriesModel> searchSeriesModel = searchSeriesModelRepository.findByUserId(id);
        Integer count = searchSeriesModelRepository.countByUserId(id);
        JSONObject body = new JSONObject();
        body.put("user", user);
        body.put("result", searchSeriesModel);
        body.put("count", count);
        return Utills.buildResponse(body,200,"");
    }
}
