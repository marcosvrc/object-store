package br.com.objectstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    public void upload(MultipartFile file) throws Exception;

    public byte[] getImage(String objectId) throws Exception;

}