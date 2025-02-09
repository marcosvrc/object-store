package br.com.objectstore.service;

import br.com.objectstore.entity.ImageEntity;
import br.com.objectstore.repository.ImageRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    private static final String BUCKET_NAME = "images";

    private final MinioClient minioClient;
    private final ImageRepository imageRepository;

    public UploadServiceImpl(MinioClient minioClient, ImageRepository imageRepository) {
        this.minioClient = minioClient;
        this.imageRepository = imageRepository;
    }

    public void upload(MultipartFile file) throws Exception {

        var objectId = UUID.randomUUID().toString();
        var inputStream = file.getInputStream();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectId)
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType("image/png")
                        .build());

       imageRepository.save(new ImageEntity(objectId));
    }

    @Override
    public byte[] getImage(String objectId) throws Exception {
        var stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectId).build());

        return IOUtils.toByteArray(stream);
    }
}
