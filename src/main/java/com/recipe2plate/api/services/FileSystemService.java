package com.recipe2plate.api.services;

import com.recipe2plate.api.utils.FileContainer;
import com.recipe2plate.api.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FileSystemService {

    private final String rootPath = "F:/springbootprojects/Recipe2Plate/photos/";

    @Named("mediaToUrl")
    public String saveImage(MultipartFile imageFile) throws Exception {
        final byte[] imageBytes = imageFile.getBytes();
        final String fileName = getGeneratedFileName(
                imageFile,
                "IMG",
                getFormattedTimeStamp(),
                "_",
                "_"
        );
        final String destination = getDestination(fileName);
        writeFileAtDestination(destination, imageBytes);
        return fileName;
    }

    @Named("mediaToUrlMp4")
    public String saveVideoFile(MultipartFile imageFile) throws Exception {
        final String partExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        if (partExtension == null || !partExtension.equals("mp4")) throw new IOException("Invalid File");
        final String fileName = getGeneratedFileName(
                imageFile,
                "MP4",
                getFormattedTimeStamp(),
                "_",
                "_"
        );
        final String destination = getDestination(fileName);
        writeFileAtDestination(destination, imageFile.getBytes());
        return fileName;
    }


    private String getDestination(String fileName) {
        return rootPath + fileName.trim();
    }


    private String getFormattedTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_hh_mm_ss"));
    }

    private static String getGeneratedFileName(MultipartFile imageFile,
                                               String prefix,
                                               String postFix,
                                               String prefixSep,
                                               String postfixSep) throws Exception {
        final FileContainer fileContainer = FileUtils.getFileNameAndExtension(imageFile);
        final String formattedFileName = FileUtils.formatFileName(fileContainer.getKey());
        return prefix + prefixSep + formattedFileName + postfixSep + postFix + "." + fileContainer.getValue();

    }

    private static void writeFileAtDestination(String destination, byte[] fileBytes) throws IOException {
        final Path path = Paths.get(destination);
        Files.write(path, fileBytes);
    }


    public FileSystemResource getResourceFile(String fileName) throws FileNotFoundException {
        final String destination = getDestination(fileName);
        final File file = new File(destination);
        if (!file.isFile()) throw new FileNotFoundException("Files doesnt seem to exists");
        return new FileSystemResource(file);
    }
}
