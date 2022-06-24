package com.example.ansi.controller;


import com.example.ansi.model.SessionModel;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.repository.UserRepository;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ansi.model.RegisterLoginModel;
import com.example.ansi.model.UserModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class AuthController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterLoginModel user, HttpServletResponse response) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        String email = user.getEmail();

        JSONObject jsonObject = new JSONObject();

        if(!email.contains("@")) {
            jsonObject.put("message", "Email is not valid");
            return Utills.buildResponse(jsonObject, 400, null);
        }
        String username = user.getUsername();
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserModel databaseUserModel = new UserModel(email,password, username);
        userRepository.save(databaseUserModel);


        SessionModel sessionModel = new SessionModel(databaseUserModel);
        sessionRepository.save(sessionModel);

        Cookie cookie = new Cookie("sessionId", sessionModel.getId());
        response.addCookie(cookie);
        jsonObject.put("message", "User created successfully");
        return Utills.buildResponse(jsonObject, 201, sessionModel.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterLoginModel user, HttpServletRequest request, HttpServletResponse response) {

        UserModel databaseUserModel = userRepository.findByEmail(user.getEmail());
        if(databaseUserModel == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "User not found");
            return Utills.buildResponse(jsonObject, 404, null);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(user.getPassword(), databaseUserModel.getPassword())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Password is incorrect");
            return Utills.buildResponse(jsonObject, 401, null);
        }

        SessionModel sessionModel = new SessionModel(databaseUserModel);
        sessionRepository.save(sessionModel);

        Cookie cookie = new Cookie("sessionId", sessionModel.getId());
        response.addCookie(cookie);
        return Utills.buildResponse(null, 200, sessionModel.getId());

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RegisterLoginModel user, HttpServletRequest request, HttpServletResponse response) {

        String sessionId = Utills.getSessionId(request);
        Boolean deleted = sessionRepository.deleteById(sessionId);
        if(!deleted) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "User not logged in");
            return Utills.buildResponse(jsonObject, 401, null);
        }
        Cookie cookie = new Cookie("sessionId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "User logged out");
        return Utills.buildResponse(jsonObject, 200, null);

    }

    //check auth
    @GetMapping("/check")
    public ResponseEntity<?> check(HttpServletRequest request) {
        String sessionId = Utills.getSessionId(request);
        SessionModel sessionModel = sessionRepository.findById(sessionId);
        JSONObject jsonObject = new JSONObject();
        if(sessionModel == null) {
            jsonObject.put("message", "User not logged in");
            return Utills.buildResponse(jsonObject, 401, null);
        }

        jsonObject.put("message", "User logged in");
        return Utills.buildResponse(jsonObject, 200, sessionModel.getId());
    }

}
