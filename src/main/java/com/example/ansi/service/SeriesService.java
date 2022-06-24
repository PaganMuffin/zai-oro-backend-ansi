package com.example.ansi.service;

import com.example.ansi.model.AddRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SeriesService {
    ResponseEntity<?> getSeries(String q, Integer p);


    ResponseEntity<?> add(AddRequestModel addRequestModel, MultipartFile file, HttpServletRequest request, HttpServletResponse response);
}
