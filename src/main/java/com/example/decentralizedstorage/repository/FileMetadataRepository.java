package com.example.decentralizedstorage.repository;

import com.example.decentralizedstorage.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetaData, Long> {

    Optional<FileMetaData> findByFileHash(String fileHash);
}
