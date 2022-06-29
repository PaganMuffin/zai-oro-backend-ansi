package com.example.ansi.repository;

import java.util.List;

import com.example.ansi.model.CommentModel;
import com.example.ansi.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<UserModel, Long>, PagingAndSortingRepository<UserModel, Long> {

    List<UserModel> findAll();

    Page<UserModel> findAll(Pageable pageable);

    Page<UserModel> findAllByUsernameContaining(String username, Pageable pageable);
    UserModel findByEmail(String email);

    Integer deleteById(String id);
    UserModel findById(String id);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel SET username = ?2, email = ?3, role = ?4 WHERE id = ?1")
    void updateUser(String id,
                   String username,
                   String email,
                   String role);


}