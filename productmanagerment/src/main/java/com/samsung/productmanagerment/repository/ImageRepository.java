package com.samsung.bookmanagerment.repository;

import com.samsung.bookmanagerment.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<UploadFile, Long> {
    UploadFile findUploadFileByOriginUrl(String url);
}
