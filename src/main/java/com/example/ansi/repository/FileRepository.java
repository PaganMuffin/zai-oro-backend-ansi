package com.example.ansi.repository;

import com.example.ansi.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FileRepository extends JpaRepository<FileModel, Long> {
    FileModel findById(String fileName);

    Integer deleteByUserId(String userId);

    Integer deleteByFilename(String filename);
}
