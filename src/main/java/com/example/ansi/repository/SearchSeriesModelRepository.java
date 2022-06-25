package com.example.ansi.repository;

import com.example.ansi.model.FileModel;
import com.example.ansi.model.search.SearchSeriesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchSeriesModelRepository extends JpaRepository<SearchSeriesModel, String> {

    @Query("SELECT s.series FROM SearchSubtitleEntryModel s WHERE s.user.id = ?1")
    List<SearchSeriesModel> findByUserId(String userId);

    @Query("SELECT count(s) FROM SearchSubtitleEntryModel s WHERE s.user.id = ?1")
    Integer countByUserId(String userId);
}
