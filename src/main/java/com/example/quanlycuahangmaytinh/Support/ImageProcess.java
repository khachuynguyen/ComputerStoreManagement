package com.example.quanlycuahangmaytinh.Support;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ImageProcess {
    public static String uploadImage(MultipartFile file) throws IOException {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        String tmp = LocalDateTime.now().format(myFormatObj);
        String fileName = tmp + StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get("uploads/");

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            return "null";
        }

    }
    public static boolean deleteImage(String tenFile){
        try {
            return  Files.deleteIfExists(Paths.get("uploads/"+tenFile));
        }catch (Exception exception){
            return false;
        }
    }
}
