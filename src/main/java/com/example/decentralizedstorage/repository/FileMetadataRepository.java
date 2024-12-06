package com.example.decentralizedstorage.repository;

import com.example.decentralizedstorage.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetaData, Long> {

    Optional<FileMetaData> findByFileHash(String fileHash);
}
