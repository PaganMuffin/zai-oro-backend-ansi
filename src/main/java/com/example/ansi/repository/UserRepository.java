package com.example.ansi.repository;

import java.util.List;

import com.example.ansi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    List<UserModel> findAll();

    UserModel findByEmail(String email);

    UserModel findById(String id);

}