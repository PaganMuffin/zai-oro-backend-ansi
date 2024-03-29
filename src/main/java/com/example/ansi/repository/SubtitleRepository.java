package com.example.ansi.repository;

import com.example.ansi.model.SubtitleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SubtitleRepository extends JpaRepository<SubtitleEntry, Long> {

    SubtitleEntry findById(String id);

    SubtitleEntry findByIdAndUserId (String id, String userId);

    void deleteById(String id);

    void deleteByUserId(String userId);
    @Modifying
    @Transactional
    @Query("UPDATE SubtitleEntry SET episode = ?2, Description = ?3, author = ?4, filename = ?5, updatedAt = ?6 WHERE id = ?1")
    void updateEntry(String id,
                    Integer episode,
                    String desc,
                    String author,
                    String filename,
                    Long updatedAt);
}
