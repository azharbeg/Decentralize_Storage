package com.example.decentralizedstorage.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "file_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false, unique = true)
    private String fileHash;  // Unique file identifier (CID)
}
