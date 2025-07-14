package org.felixcjy.controller.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.felixcjy.infrastructure.service.MinIOService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 操作 minio
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:53
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/test/minio")
@RequiredArgsConstructor
@Tag(name = "测试-MinIO 操作接口")
public class MinioController {
    private final MinIOService minioService;

    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "objectName", required = false) String objectName) throws Exception {
        String finalObjectName = minioService.uploadFile(file, objectName);
        return ResponseEntity.ok(finalObjectName);
    }

    @GetMapping("/download")
    @Operation(summary = "下载文件")
    public ResponseEntity<InputStreamResource> download(@RequestParam("objectName") String objectName) throws Exception {
        InputStream is = minioService.downloadFile(objectName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + objectName)
                .body(new InputStreamResource(is));
    }

    @GetMapping("/presigned-url")
    @Operation(summary = "预览链接")
    public ResponseEntity<String> getPresignedUrl(@RequestParam("objectName") String objectName,
                                                  @RequestParam(value = "expires", defaultValue = "3600") int expires) throws Exception {
        String url = minioService.getPresignedUrl(objectName, expires);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文件")
    public ResponseEntity<String> delete(@RequestParam("objectName") String objectName) throws Exception {
        minioService.delete(objectName);
        return ResponseEntity.ok("删除成功: " + objectName);
    }

    @GetMapping("/exists")
    @Operation(summary = "检查文件是否存在")
    public ResponseEntity<Boolean> exists(@RequestParam("objectName") String objectName) throws Exception {
        boolean exists = minioService.exists(objectName);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/list")
    @Operation(summary = "列出对象（文件）")
    public ResponseEntity<List<String>> listObjects(@RequestParam(value = "prefix", required = false) String prefix,
                                                    @RequestParam(value = "recursive", defaultValue = "true") boolean recursive) throws Exception {
        List<String> objects = minioService.listObjects(prefix, recursive);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/listBuckets")
    @Operation(summary = "Bucket 列表")
    public ResponseEntity<List<String>> listBuckets() throws Exception {
        return ResponseEntity.ok(minioService.listAllBuckets());
    }

    @PostMapping("/createBucket")
    @Operation(summary = "创建 Bucket")
    public ResponseEntity<String> createBucket(@RequestParam String bucketName) throws Exception {
        minioService.createBucket(bucketName);
        return ResponseEntity.ok("创建成功: " + bucketName);
    }

    @DeleteMapping("/deleteBucket")
    @Operation(summary = "删除 Bucket")
    public ResponseEntity<String> deleteBucket(@RequestParam String bucketName) throws Exception {
        minioService.deleteBucket(bucketName);
        return ResponseEntity.ok("删除成功: " + bucketName);
    }

    @GetMapping("/bucket/exists")
    @Operation(summary = "检查 Bucket 是是否存在")
    public ResponseEntity<Boolean> bucketExists(@RequestParam String bucketName) throws Exception {
        return ResponseEntity.ok(minioService.bucketExists(bucketName));
    }
}