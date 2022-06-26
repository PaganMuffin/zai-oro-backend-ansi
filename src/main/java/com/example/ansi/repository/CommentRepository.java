package com.example.ansi.repository;

import com.example.ansi.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentModel, Long>{

}
