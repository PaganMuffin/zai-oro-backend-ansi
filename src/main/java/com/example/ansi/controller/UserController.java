package com.example.ansi.controller;

import com.example.ansi.model.SeriesModel;
import com.example.ansi.model.SessionModel;
import com.example.ansi.model.UserModel;
import com.example.ansi.model.anilist.Response;
import com.example.ansi.model.search.SearchSeriesModel;
import com.example.ansi.repository.SearchRepository;
import com.example.ansi.repository.SearchSeriesModelRepository;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.repository.UserRepository;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private SearchSeriesModelRepository searchSeriesModelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

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

    @GetMapping("/user/@me")
    public ResponseEntity<?> testGetMe(HttpServletRequest request, HttpServletResponse response) {

        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();
        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body,401,"");
        }
        UserModel user = session.getUser();
        body.put("username", user.getUsername());
        body.put("id", user.getId());
        body.put("role", user.getRole());
        return Utills.buildResponse(body,200,"");
    }
}
