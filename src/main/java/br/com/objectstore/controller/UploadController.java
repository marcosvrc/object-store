package br.com.objectstore.controller;

import br.com.objectstore.service.UploadService;
import io.minio.MinioClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
    @RequestMapping("/bucket/v1")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/image/upload")
    public void upload(@RequestParam MultipartFile file) throws Exception {
        uploadService.upload(file);
    }

    @GetMapping(value = "/image/{objectId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String objectId) throws Exception {
        return uploadService.getImage(objectId);
    }

}
