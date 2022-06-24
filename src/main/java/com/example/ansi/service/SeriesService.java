package com.example.ansi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface SeriesService {
    ResponseEntity<?> getSeries(String q, Integer p);


}
