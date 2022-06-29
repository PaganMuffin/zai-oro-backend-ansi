package com.example.ansi.repository;

import com.example.ansi.model.SessionModel;
import com.example.ansi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SessionRepository extends JpaRepository<SessionModel, Long> {
    List<SessionModel> findAll();

    SessionModel findById(String sessionId);

    //delete by id
    void deleteById(String sessionId);
}

