package com.example.ansi.service;


import com.example.ansi.model.*;
import com.example.ansi.model.anilist.AniListEntry;
import com.example.ansi.repository.FileRepository;
import com.example.ansi.repository.SeriesRepository;
import com.example.ansi.repository.SessionRepository;
import com.example.ansi.repository.SubtitleRepository;
import com.example.ansi.utills.AniList;
import com.example.ansi.utills.Utills;
import com.mashape.unirest.http.exceptions.UnirestException;
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

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SubtitleRepository subtitleRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public ResponseEntity<?> getSeries(String q, Integer p) {
        return null;
    }

    @Override
    public ResponseEntity<?> add(AddRequestModel addRequestModel, MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws UnirestException {

        String sessionId = Utills.getSessionId(request);
        JSONObject body = new JSONObject();

        if (sessionId == null) {
            body.put("message", "You are not logged in");
            return Utills.buildResponse(body, 401, null);
        }

        String ext = Utills.getFileExtension(file.getOriginalFilename());
        if (!Utills.ALLOWED_EXTENSIONS.contains(ext)) {
            body.put("message", "File type not allowed");
            return Utills.buildResponse(body, 400, null);
        }

        SessionModel session = sessionRepository.findById(sessionId);
        if (session == null) {
            body.put("message", "You are not logged in");
            return Utills.buildResponse(body, 401, null);
        }

        SeriesModel seriesInDB = seriesRepository.getByAlId(addRequestModel.getShowId());
        if (seriesInDB == null) {
            // get series from anilist
            // save series in db
            AniListEntry seriesFromAniList = AniList.getById(addRequestModel.getShowId());
            if (seriesFromAniList == null) {
                body.put("message", "Series not found on AniList");
                return Utills.buildResponse(body, 404, null);
            }

            seriesInDB = new SeriesModel();
            seriesInDB.setAlId(seriesFromAniList.getId());
            seriesInDB.setDescription(seriesFromAniList.getDescription());
            seriesInDB.setEpisodes(seriesFromAniList.getEpisodes());
            seriesInDB.setSeason(seriesFromAniList.getSeason());
            seriesInDB.setSeasonYear(seriesFromAniList.getSeasonYear());
            seriesInDB.setTitle(seriesFromAniList.getTitle());
            seriesInDB.setType(seriesFromAniList.getType());
            seriesInDB.setCoverImage(seriesFromAniList.getCoverImage());
            seriesInDB.setIdMal(seriesFromAniList.getIdMal());
            seriesRepository.save(seriesInDB);

        }

        FileModel fileModel = new FileModel(file.getOriginalFilename(), session.getUser());
        fileRepository.save(fileModel);


        String newFileName = fileModel.getId() + "." + ext;
        Boolean fileSaved = storageService.save(file, newFileName);
        if (!fileSaved) {
            body.put("message", "File could not be saved");
            return Utills.buildResponse(body, 500, null);
        }
        SubtitleEntry subtitleEntry = new SubtitleEntry(addRequestModel, newFileName, session.getUser(), seriesInDB);

        subtitleRepository.save(subtitleEntry);

        body.put("message", "Entry added");
        body.put("id", subtitleEntry.getId());

        return Utills.buildResponse(body, 200, null);

    }


}
