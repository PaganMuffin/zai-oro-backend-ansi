package com.example.ansi.controller;

import com.example.ansi.model.CommentModel;
import com.example.ansi.model.SessionModel;
import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.CommentRepository;
import com.example.ansi.repository.SearchRepository;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.repository.SubtitleRepository;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost"}, allowedHeaders = "*", allowCredentials = "true")
public class CommentsController {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SubtitleRepository subtitleRepository;

    @Autowired
    private CommentRepository commentRepository;

    //id is the id of the entry
    @GetMapping("/comments/{id}")
    public List<CommentModel> getComments(@PathVariable String id) {
        List<CommentModel> comments = commentRepository.findByEntrieId(id);
        return comments;

    }

    //id is the id of the entry
    @PostMapping("/comment/{id}")
    public ResponseEntity<?> postComment(@PathVariable String id, @RequestBody String content, HttpServletRequest request, HttpServletResponse response) {
        String sessionId = Utills.getSessionId(request);
        SubtitleEntry subtitleEntry = subtitleRepository.findById(id);
        JSONObject body = new JSONObject();
        SessionModel session = sessionRepository.findById(sessionId);
        if (session == null) {
            body.put("message", "You are not logged in");
            return Utills.buildResponse(body, 401, null);
        }

        CommentModel comment = new CommentModel(
                content,
                session.getUser(),
                subtitleEntry
        );

        commentRepository.save(comment);


        body.put("message", "Comment added");
        return Utills.buildResponse(body, 200, null);
    }
}
