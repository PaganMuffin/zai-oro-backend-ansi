package com.example.ansi.repository;

import com.example.ansi.model.SubtitleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtitleRepository extends JpaRepository<SubtitleEntry, Long> {

}
