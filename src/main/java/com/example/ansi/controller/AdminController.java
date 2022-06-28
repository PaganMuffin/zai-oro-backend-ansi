package com.example.ansi.controller;

import com.example.ansi.model.CommentModel;
import com.example.ansi.model.SessionModel;
import com.example.ansi.model.UserModel;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.CommentRepository;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.repository.UserRepository;
import com.example.ansi.service.SearchService;
import com.example.ansi.utills.Utills;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/users")
    public ResponseEntity<?>  AdminUsers( @RequestParam(required = false) Integer p,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {

        p = p == null ? 1 : p;

        //CHECK IF USER IS ADMIN
        String sessionId = Utills.getSessionId(request);

        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            return Utills.buildResponse(null, 401, "Unauthorized");
        }

        PageRequest pageReq
                = PageRequest.of(p-1, 10);

        Page<UserModel> results = userRepository.findAll(pageReq);
        JSONObject body = new JSONObject();
        body.put("result", results.getContent());
        body.put("hasNext", results.hasNext());

        return Utills.buildResponse(body, 200, "");
    }

    @GetMapping("/admin/comments")
    public ResponseEntity<?> AdminComments(@RequestParam(required = false) Integer p,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        p = p == null ? 1 : p;

        //CHECK IF USER IS ADMIN
        String sessionId = Utills.getSessionId(request);

        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            return Utills.buildResponse(null, 401, "Unauthorized");
        }

        PageRequest pageReq
                = PageRequest.of(p-1, 10);

        Page<CommentModel> results = commentRepository.findAll(pageReq);
        JSONObject body = new JSONObject();
        body.put("result", results.getContent());
        body.put("hasNext", results.hasNext());

        return Utills.buildResponse(body, 200, "");


    }



    @GetMapping("/admin/entries")
    public ResponseEntity<?> AdminEntries(@RequestParam(required = false) String q,
                                          @RequestParam(required = false) Integer p,
                                          @RequestParam(required = false) Integer limit,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws UnirestException {

        q = q == null ? "" : q;
        p = p == null ? 1 : p;
        limit = limit == null ? 10 : limit;
        JSONObject result = searchService.search(q, p, limit);
        return Utills.buildResponse(result, 200, "");
    }

}
