package com.example.ansi.service;

import com.example.ansi.model.AddRequestModel;
import com.example.ansi.model.EditRequestModel;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SeriesService {
    ResponseEntity<?> getSeries(String seriesId, HttpServletRequest request, HttpServletResponse response);


    ResponseEntity<?> add(AddRequestModel addRequestModel, MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws UnirestException;

    ResponseEntity<?> edit(String id, EditRequestModel editRequestModel, MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws UnirestException;


}
