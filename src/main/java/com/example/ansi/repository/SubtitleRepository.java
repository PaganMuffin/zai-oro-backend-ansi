package com.example.ansi.repository;

import com.example.ansi.model.SubtitleEntry;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubtitleRepository extends JpaRepository<SubtitleEntry, Long> {

    @Query("SELECT s FROM SearchSubtitleEntryModel s WHERE s.series.title.romaji LIKE %?1%")
    List<SearchSubtitleEntryModel> searchAllBySeriesTitleRomaji(String title);

    SubtitleEntry findById(String id);
}
