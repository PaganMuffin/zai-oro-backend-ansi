package com.example.ansi.service;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import com.example.ansi.repository.SearchRepository;
import com.example.ansi.repository.SubtitleRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<SearchSubtitleEntryModel> search(String q, Integer p) {
        List<SearchSubtitleEntryModel> results = searchRepository.searchAllBySeriesTitleRomaji(q);
        return results;
    }
}
