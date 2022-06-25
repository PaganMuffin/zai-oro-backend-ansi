package com.example.ansi.repository;

import com.example.ansi.model.SeriesModel;
import com.example.ansi.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeriesRepository extends JpaRepository<SeriesModel, Long> {

    SeriesModel getById(String id);

    SeriesModel getByAlId(Integer alId);
}
