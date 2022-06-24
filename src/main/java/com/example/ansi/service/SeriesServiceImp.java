package com.example.ansi.service;


import com.example.ansi.model.AddRequestModel;
import com.example.ansi.model.SessionModel;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class SeriesServiceImp implements SeriesService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public ResponseEntity<?> getSeries(String q, Integer p) {
        return null;
    }

    @Override
    public ResponseEntity<?> add(AddRequestModel addRequestModel, MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();

        if (sessionId == null) {
            body.put("message", "You are not logged in");
            return Utills.buildResponse(body, 401, null);
        }

        String ext = Utills.getFileExtension(file.getContentType());
        if (!Utills.ALLOWED_EXTENSIONS.contains(ext)) {
            body.put("message", "File type not allowed");
            return Utills.buildResponse(body, 400, null);
        }

        //check if sessionId in DB
        //if not, return 401
        //if yes, add series to DB
        //if yes, return 200
        SessionModel session = sessionRepository.findById(sessionId);
        if (session == null) {
            body.put("message", "You are not logged in");
            return Utills.buildResponse(body, 401, null);
        }


        return Utills.buildResponse(body, 200, null);

    }


}
