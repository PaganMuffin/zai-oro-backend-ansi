package com.example.ansi.repository;

import com.example.ansi.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentModel, Long>{

    List<CommentModel> findByEntrieId(String id);
}
