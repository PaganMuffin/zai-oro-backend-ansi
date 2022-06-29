package com.example.ansi.controller;

import com.example.ansi.model.*;
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
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?>  AdminUsers(@RequestParam(required = false) String q, @RequestParam(required = false) Integer p,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {

        p = p == null ? 1 : p;
        q = q == null ? "" : q;
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

        Page<UserModel> results = userRepository.findAllByUsernameContaining(q,pageReq);

        List<UserAdminModel> result = results.getContent().stream()
                .map(UserAdminModel::new)
                .collect(Collectors.toList());

        body.put("result",result);
        body.put("hasNext", results.hasNext());

        return Utills.buildResponse(body, 200, "");
    }

    @GetMapping("/admin/comments")
    public ResponseEntity<?> AdminComments(@RequestParam(required = false) String q,
                                           @RequestParam(required = false) Integer p,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        p = p == null ? 1 : p;
        q = q == null ? "" : q;
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

        Page<CommentModel> results = commentRepository.findAllBySearch(q,pageReq);
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

        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();

        SessionModel session = sessionRepository.findById(sessionId);
        if(session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body, 401, "");
        }

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


        SubtitleEntry model = subtitleRepository.findById(id);


        if(model != null) {
            commentRepository.deleteByEntrieId(id);
            subtitleRepository.deleteById(id);
            fileRepository.deleteByFilename(model.getFilename());
            body.put("message", "Deleted");
            return Utills.buildResponse(body, 200, "");
        } else {
            body.put("message", "Error deleting entry");
            return Utills.buildResponse(body, 404, "");
        }

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

        if(session.getUser().getId().equals(id)){
            body.put("message", "You can't delete yourself");
            return Utills.buildResponse(body, 401, "");
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


    //update user
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<?> AdminUserUpdate(@RequestBody UserAdminUpdateModel user, @PathVariable String id,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws UnirestException {
        //CHECK IF USER IS ADMIN
        JSONObject body = new JSONObject();
        String sessionId = Utills.getSessionId(request);
        SessionModel session = sessionRepository.findById(sessionId);
        if (session == null || !session.getUser().getRole().equals("admin")) {
            body.put("message", "Unauthorized");
            return Utills.buildResponse(body, 401, "");
        }
        UserModel userModel = userRepository.findById(id);
        if (userModel == null) {
            body.put("message", "User not found");
            return Utills.buildResponse(body, 404, "");
        }

        userRepository.updateUser(id, user.getUsername(), user.getEmail(), user.getRole());


        body.put("message", "Updated");
        return Utills.buildResponse(body, 200, "");

    }

}
