package com.example.ansi.repository;

import com.example.ansi.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileModel, Long> {
    FileModel findById(String fileName);
}
