package com.example.quanlycuahangmaytinh.Controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/image")
public class ImageController {
    @GetMapping("{photo}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable(name = "photo")String photo){
        if (!photo.equals("") || photo !=null) {
            try {
                Path fileName = Paths.get("uploads", photo);
                byte[] buffer = Files.readAllBytes(fileName);
                ByteArrayResource br = new ByteArrayResource(buffer);
                return ResponseEntity.ok().contentLength(buffer.length).contentType(MediaType.parseMediaType("image/png")).body(br);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
