package com.example.ansi.controller;


import com.example.ansi.model.SessionModel;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.repository.UserRepository;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.ansi.model.RegisterLoginModel;
import com.example.ansi.model.UserModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(origins = {"http://ansi.localhost:3000", "http://localhost"}, allowedHeaders = "*", allowCredentials = "true")
public class AuthController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterLoginModel user, HttpServletResponse response, HttpServletRequest request) {

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
        try{
            userRepository.save(databaseUserModel);
        } catch (Exception e) {
            jsonObject.put("message", "User already exists");
            return Utills.buildResponse(jsonObject, 400, null);
        }


        SessionModel sessionModel = new SessionModel(databaseUserModel);
        sessionRepository.save(sessionModel);

        Cookie cookie = new Cookie("sessionId", sessionModel.getId());
        response.addCookie(cookie);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setSecure(true);
        cookie.setDomain(request.getServerName());
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        jsonObject.put("message", "User created successfully");
        jsonObject.put("user", databaseUserModel.getId());
        return Utills.buildResponse(jsonObject, 201, sessionModel.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterLoginModel user, HttpServletRequest request, HttpServletResponse response) {

        UserModel databaseUserModel = userRepository.findByEmail(user.getEmail());
        JSONObject jsonObject = new JSONObject();
        if(databaseUserModel == null) {
            jsonObject.put("message", "User not found");
            return Utills.buildResponse(jsonObject, 404, null);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(user.getPassword(), databaseUserModel.getPassword())) {
            jsonObject.put("message", "Password is incorrect");
            return Utills.buildResponse(jsonObject, 401, null);
        }

        SessionModel sessionModel = new SessionModel(databaseUserModel);
        sessionRepository.save(sessionModel);

        Cookie cookie = new Cookie("sessionId", sessionModel.getId());
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setSecure(true);
        cookie.setDomain(request.getServerName());
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        jsonObject.put("message", "User logged in successfully");
        jsonObject.put("user", databaseUserModel.getId());
        return Utills.buildResponse(jsonObject, 200, sessionModel.getId());

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
