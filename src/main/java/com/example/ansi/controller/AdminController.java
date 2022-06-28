package com.example.ansi.controller;

import com.example.ansi.model.CommentModel;
import com.example.ansi.model.SessionModel;
import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.UserModel;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.*;
import com.example.ansi.service.SearchService;
import com.example.ansi.utills.Utills;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.minidev.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.provider.HibernateUtils;
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

    @Autowired
    private SubtitleRepository subtitleRepository;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/admin/users")
    public ResponseEntity<?>  AdminUsers( @RequestParam(required = false) Integer p,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {

        p = p == null ? 1 : p;

        //CHECK IF USER IS ADMIN
        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();

        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(null, 401, "");
        }

        PageRequest pageReq
                = PageRequest.of(p-1, 10);

        Page<UserModel> results = userRepository.findAll(pageReq);
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
        JSONObject body = new JSONObject();

        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body, 401, "");
        }

        PageRequest pageReq
                = PageRequest.of(p-1, 10);

        Page<CommentModel> results = commentRepository.findAll(pageReq);
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


    @DeleteMapping("/admin/entries/{id}")
    public ResponseEntity<?> AdminEntriesDelete(@PathVariable String id,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws UnirestException {
        //start transaction


        //CHECK IF USER IS ADMIN
        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();
        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body, 401, "");
        }

        commentRepository.deleteByEntrieId(id);

        SubtitleEntry isDeleted = subtitleRepository.deleteById(id);

        if(isDeleted != null) {
            fileRepository.deleteByFilename(isDeleted.getFilename());
            body.put("message", "Deleted");
        } else {
            body.put("message", "Error deleting entry");
        }

        return Utills.buildResponse(body, 404, "");
    }


    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<?> AdminCommentsDelete(@PathVariable String id,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws UnirestException {
        //CHECK IF USER IS ADMIN
        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();
        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body, 401, "");
        }
        Integer isDeleted = commentRepository.deleteById(id);
        if(isDeleted == 1) {
            body.put("message", "Deleted");
            return Utills.buildResponse(body, 200, "");
        } else {
            body.put("message", "Error deleting comment");
            return Utills.buildResponse(body, 404, "");
        }
    }


    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> AdminUsersDelete(@PathVariable String id,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws UnirestException {
        //CHECK IF USER IS ADMIN
        JSONObject body = new JSONObject();
        String sessionId = Utills.getSessionId(request);
        SessionModel session = sessionRepository.findById(sessionId);

        if(session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body, 401, "Unauthorized");
        }

        commentRepository.deleteByUserId(id);
        subtitleRepository.deleteByUserId(id);
        fileRepository.deleteByUserId(id);

        Integer isDeleted = userRepository.deleteById(id);
        if(isDeleted == 1) {


            body.put("message", "Deleted");
            return Utills.buildResponse(body, 200, "");

        } else {
            body.put("message", "Error deleting user");
            return Utills.buildResponse(body, 404, "");
        }
    }

}
