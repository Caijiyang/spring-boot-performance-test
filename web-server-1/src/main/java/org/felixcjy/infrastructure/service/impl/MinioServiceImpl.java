package org.felixcjy.infrastructure.service.impl;

import io.minio.*;
import org.felixcjy.properties.MinioProperties;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.felixcjy.infrastructure.service.MinIOService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:47
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements MinIOService {
    private final MinioClient minioClient;

    private final MinioProperties properties;

    @Override
    public boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    @Override
    public void createBucket(String bucketName) throws Exception {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @Override
    public void deleteBucket(String bucketName) throws Exception {
        if (bucketExists(bucketName)) {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @Override
    public List<String> listAllBuckets() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        List<String> names = new ArrayList<>();
        for (Bucket b : buckets) {
            names.add(b.name());
        }
        return names;
    }

    @Override
    public String uploadFile(MultipartFile file, String objectName) throws Exception {
        return uploadFile(file.getInputStream(), file.getContentType(), file.getSize(),
                objectName != null ? objectName : UUID.randomUUID() + "_" + file.getOriginalFilename());
    }

    @Override
    public String uploadFile(InputStream stream, String contentType, long size, String objectName) throws Exception {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .stream(stream, size, -1)
                .contentType(contentType)
                .build();
        minioClient.putObject(args);
        return objectName;
    }

    @Override
    public InputStream downloadFile(String objectName) throws Exception {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build();
        return minioClient.getObject(args);
    }

    @Override
    public String getPresignedUrl(String objectName, int expires) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .method(Method.GET)
                .expiry(expires, TimeUnit.SECONDS)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    @Override
    public boolean exists(String objectName) throws Exception {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(properties.getBucketName())
                    .object(objectName)
                    .build());
            return true;
        } catch (ErrorResponseException e) {
            return false;
        }
    }

    @Override
    public void delete(String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build());
    }

    @Override
    public List<String> listObjects(String prefix, boolean recursive) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(properties.getBucketName())
                .prefix(prefix != null ? prefix : "")
                .recursive(recursive)
                .build());

        List<String> objectNames = new ArrayList<>();
        for (Result<Item> result : results) {
            objectNames.add(result.get().objectName());
        }
        return objectNames;
    }

    @Override
    public String getContentType(String objectName) throws Exception {
        StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build());
        return stat.contentType();
    }
}

