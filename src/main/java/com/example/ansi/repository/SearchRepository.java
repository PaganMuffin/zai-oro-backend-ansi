package com.example.ansi.repository;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchSubtitleEntryModel, Long>, PagingAndSortingRepository<SearchSubtitleEntryModel, Long> {

    @Query("SELECT s FROM SearchSubtitleEntryModel s WHERE s.series.title.romaji LIKE %?1%")
    Page<SearchSubtitleEntryModel> searchAllBySeriesTitleRomaji(String title, Pageable pageReq);

    SearchSubtitleEntryModel findById(String id);
}
