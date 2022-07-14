package com.recipe2plate.api.utils;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Component
public class FileUtils {

    public static String formatFileName(String fileName) {
        return fileName.replace(" ", "")
                .replace(Pattern.compile("([ ():])").pattern(), "_");
    }


    public static FileContainer getFileNameAndExtension(MultipartFile imageFile) throws Exception {
        final String fileNameWithExtension = StringUtils.getFilename(imageFile.getOriginalFilename());
        final String filenameExtension = StringUtils.getFilenameExtension(fileNameWithExtension);

        if (fileNameWithExtension == null || filenameExtension == null) throw new Exception("Invalid File");
        final String fileName = removeFileExtension(fileNameWithExtension, true);
        return FileContainer.builder()
                .key(fileName)
                .value(filenameExtension)
                .build();
    }


    public static String removeFileExtension(String fileName, Boolean removeAllExtensions) {
        if (fileName.isEmpty()) return fileName;
        final String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return fileName.replace(Pattern.compile(extPattern).pattern(), "");
    }

}
