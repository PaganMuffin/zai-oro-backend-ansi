package com.example.ansi.repository;

import java.util.List;

import com.example.ansi.model.CommentModel;
import com.example.ansi.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<UserModel, Long>, PagingAndSortingRepository<UserModel, Long> {

    List<UserModel> findAll();

    Page<UserModel> findAll(Pageable pageable);

    UserModel findByEmail(String email);

    UserModel findById(String id);

}