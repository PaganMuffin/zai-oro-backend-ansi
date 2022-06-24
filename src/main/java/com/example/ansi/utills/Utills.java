package com.example.ansi.utills;

import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.function.ServerRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class Utills {


    public static final List<String> ALLOWED_EXTENSIONS = List.of("ass");

    public static ResponseEntity<?> buildResponse(JSONObject body, int status, String token) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(body, headers, status);
    }


    public static String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
