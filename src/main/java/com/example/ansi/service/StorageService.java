package com.example.ansi.service;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    //MAIN FOLDER NAME
    public static final String UPLOAD_FOLDER = "subtitles";

    public Boolean init() throws IOException;

    public Boolean save(MultipartFile file, String fileName);

    public Boolean delete(String filename);

    public UrlResource getFile(String filename);


}
