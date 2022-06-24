package com.example.ansi.repository;

import com.example.ansi.model.SeriesModel;
import com.example.ansi.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<SeriesModel, Long> {
}
