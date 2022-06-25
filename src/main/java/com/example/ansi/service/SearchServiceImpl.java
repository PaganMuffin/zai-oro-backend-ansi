package com.example.ansi.service;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.SearchRepository;
import com.example.ansi.repository.SubtitleRepository;
import net.minidev.json.JSONObject;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public JSONObject search(String q, Integer p, Integer limit) {
        PageRequest pageReq
                = PageRequest.of(p-1, limit);

        Page<SearchSubtitleEntryModel> results = searchRepository.searchAllBySeriesTitleRomaji(q, pageReq);
        JSONObject body = new JSONObject();
        body.put("result", results.getContent());
        body.put("hasNext", results.hasNext());
        return body;
    }
}
