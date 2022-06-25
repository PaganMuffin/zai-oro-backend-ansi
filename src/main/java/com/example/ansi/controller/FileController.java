package com.example.ansi.controller;

import com.example.ansi.model.FileModel;
import com.example.ansi.repository.FileRepository;
import com.example.ansi.service.StorageService;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

    @Autowired
    StorageService storageService;

    @Autowired
    FileRepository fileRepository;

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        String uid = fileName.split("\\.")[0];
        FileModel file = fileRepository.findById(uid);
        if(file == null) {
            JSONObject body = new JSONObject();
            body.put("message", "File not found");
            return (ResponseEntity<Resource>) Utills.buildResponse(body,404,"");
        }
        Resource resource = storageService.getFile(fileName);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
                .header("Content-Type", "application/octet-stream")
                .body(resource);
    }
}
