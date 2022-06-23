package com.example.ansi.repository;

import com.example.ansi.model.SessionModel;
import com.example.ansi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    List<SessionModel> findAll();

    SessionModel findById(String sessionId);

    //delete by id
    Boolean deleteById(String sessionId);
}

