package com.github.leandro.converterapi.resources;

import com.github.leandro.converterapi.models.dtos.FileMetadataDto;
import com.github.leandro.converterapi.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileResource {

    private FileService fileService;

    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public ResponseEntity<ByteArrayResource> upload(@RequestParam("file") MultipartFile file) {
        try {
            FileMetadataDto fileMetadata = this.fileService.parse(file);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMetadata.getTarget().getName() + "\"")
                .contentLength(fileMetadata.getTarget().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileMetadata.getResource());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

