package com.reviews.Directory.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadpath = Paths.get(uploadDir);

        if(!Files.exists(uploadpath)) {
            Files.createDirectories(uploadpath); // Exception added
        }

        try(InputStream inputStream = multipartFile.getInputStream()){ // InputStream: Java.io
            Path filePath = uploadpath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException e) {
            throw new IOException("Could not safe file"+ fileName, e );
        }

    }

}
