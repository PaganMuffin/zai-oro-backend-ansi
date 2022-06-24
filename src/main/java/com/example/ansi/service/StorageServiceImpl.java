package com.example.ansi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {


    private final Path path;

    @Autowired
    public StorageServiceImpl() {
        this.path = Paths.get(UPLOAD_FOLDER);
    }

    @Override
    public Boolean init() {
        try {
            Files.createDirectory(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Boolean save(MultipartFile file, String fileName) {
        try {
            if (!file.isEmpty()) {
                Files.copy(file.getInputStream(), path.resolve(fileName));
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Boolean delete(String filename) {
        try {
            Files.delete(path.resolve(filename));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public UrlResource getFile(String filename) {
        try {
            Path file = path.resolve(filename);
            return new UrlResource(file.toUri());
        } catch (IOException e) {
            return null;
        }

    }
}

