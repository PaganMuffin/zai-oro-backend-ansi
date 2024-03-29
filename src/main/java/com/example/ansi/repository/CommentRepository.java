package com.example.ansi.repository;

import com.example.ansi.model.CommentModel;
import com.example.ansi.model.search.SearchSubtitleEntryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface CommentRepository extends JpaRepository<CommentModel, Long>, PagingAndSortingRepository<CommentModel, Long> {

    List<CommentModel> findByEntrieId(String id);

    CommentModel findById(String id);

    Integer deleteById(String id);

    Integer deleteByEntrieId(String id);

    Integer deleteByUserId(String userId);

    Page<CommentModel> findAll(Pageable pageable);

    @Query("SELECT c FROM CommentModel c WHERE c.user.username LIKE %?1% OR c.content LIKE %?1%")
    Page<CommentModel> findAllBySearch(String search, Pageable pageable);
    //Page<CommentModel> findallbyUsernameContainingOrContentContaining(String str, Pageable pageable);
}
