package com.recipe2plate.api.controllers;


import com.recipe2plate.api.services.FileSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class FileSystemController {
    public final FileSystemService fileSystemService;

    private String getAccessUrl(String requestUrl, String fileName) throws Exception {
        if (requestUrl == null) throw new Exception("Url not found");
        return requestUrl + "/" + fileName;
    }


    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<FileSystemResource> getFile(@PathVariable String fileName) throws FileNotFoundException {
        final FileSystemResource resource = fileSystemService.getImage(fileName);
        return ResponseEntity.ok().body(resource);
    }
}
