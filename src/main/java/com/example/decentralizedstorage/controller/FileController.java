package com.example.decentralizedstorage.controller;

import com.example.decentralizedstorage.entity.FileMetaData;
import com.example.decentralizedstorage.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
    Upload File: The user can upload a file via the /upload endpoint. The file’s hash will be returned as the unique file identifier (CID).
    Download File: The user can download the file using the CID via the /download/{fileHash} endpoint.
 */

@RestController
@RequestMapping("/api/files")
public class FileController {



    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
        System.out.println("FileController bean initialized");

    }


    /*
        Handles file uploads via an HTTP POST request.
           -@RequestParam("file") MultipartFile file: Captures the uploaded file from the client.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileHash = fileService.uploadFile(file); // Invokes the uploadFile method in FileService, which:
                                                            /*
                                                                Generates a unique hash (CID) for the file.
                                                                Saves the file to the disk.
                                                                Stores metadata in the database.
                                                            */
            return ResponseEntity.ok(fileHash);  //Returns the file hash (CID) as the response body with an HTTP 200 status.

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());  // Returns an HTTP 500 status with an error message if an exception occurs.
        }
    }

    @GetMapping("/download/{fileHash}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileHash) {  // Captures the fileHash (CID) from the URL.
        try {
            FileMetaData metadata = fileService.getFileMetadata(fileHash);  // Retrieves file metadata and the actual file content using the file hash.
            byte[] fileBytes = fileService.getFile(fileHash);
            return ResponseEntity.ok(fileBytes);   //  Returns the file's byte content as the response body with an HTTP 200 status.
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);   // Returns an HTTP 404 status if the file or metadata is not found.
        }
    }
}
