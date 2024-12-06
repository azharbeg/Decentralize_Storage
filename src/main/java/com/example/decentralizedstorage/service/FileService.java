package com.example.decentralizedstorage.service;

import com.example.decentralizedstorage.entity.FileMetaData;
import com.example.decentralizedstorage.entity.FileMetaData;
import com.example.decentralizedstorage.repository.FileMetadataRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/*
    This service enables:-

    Secure and efficient file storage with content-based addressing (via SHA-256 hashes).
    Metadata storage for easy management and retrieval.
    File access with unique identifiers, making it ideal for decentralized storage systems.
 */

@Service
public class FileService {

    @Value("${file.upload.dir}") // Injects the file upload directory path from the application properties
    private String uploadDir;

    private final FileMetadataRepository fileMetadataRepository;

    // Injects the FileMetadataRepository dependency into the service via the constructor
    public FileService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public String uploadFile(MultipartFile file) throws IOException {

        // Step 1: Create unique file hash (CID)
        String fileHash = generateFileHash(file);

        // Step 2: Save file to local disk (in a real system, this would be a distributed file system)
        Path path = Paths.get(uploadDir + "/" + fileHash);
        Files.write(path, file.getBytes());

        // Step 3: Store metadata in PostgreSQL
        FileMetaData metadata = new FileMetaData();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileSize(file.getSize());
        metadata.setFileHash(fileHash);

        fileMetadataRepository.save(metadata);

        return fileHash;
    }

    //Generates a SHA-256 hash of the file's content using the Apache Commons Codec library.
    private String generateFileHash(MultipartFile file) throws IOException {
        return DigestUtils.sha256Hex(file.getInputStream());   // Reads the file content via file.getInputStream().
                            // Computes the SHA-256 hash and returns it as a string.
    }

    /*
        Retrieves metadata for a file using its hash.
            -Queries the database for the metadata using fileHash.
            -Throws an exception if no metadata is found.
     */
    public FileMetaData getFileMetadata(String fileHash) {
        return fileMetadataRepository.findByFileHash(fileHash)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    /*
        Retrieves the actual file content using its hash.
            -Reads the file from the disk at the specified path (uploadDir + fileHash).
            -Returns the file's byte content, which can be streamed or returned in an API response.
     */
    public byte[] getFile(String fileHash) throws IOException {
        Path path = Paths.get(uploadDir + "/" + fileHash);
        return Files.readAllBytes(path);
    }


}

