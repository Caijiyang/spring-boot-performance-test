package org.felixcjy.infrastructure.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:46
 */
public interface MinIOService {
    /** 判断 Bucket 是否存在 */
    boolean bucketExists(String bucketName) throws Exception;

    /** 创建 Bucket（不存在时） */
    void createBucket(String bucketName) throws Exception;

    /** 删除 Bucket（必须为空） */
    void deleteBucket(String bucketName) throws Exception;

    /** 获取所有 Bucket 名称 */
    List<String> listAllBuckets() throws Exception;

    /**
     * 上传文件（自动生成或指定文件名）
     *
     * @param file       Multipart 文件
     * @param objectName 可选对象名（可为 null）
     * @return 最终对象名
     */
    String uploadFile(MultipartFile file, String objectName) throws Exception;

    /**
     * 使用流上传文件
     *
     * @param stream      文件流
     * @param contentType 内容类型
     * @param size        文件大小
     * @param objectName  对象名称
     * @return 对象名称
     */
    String uploadFile(InputStream stream, String contentType, long size, String objectName) throws Exception;

    /** 下载文件，返回 InputStream（可用于构造响应流） */
    InputStream downloadFile(String objectName) throws Exception;

    /**
     * 获取对象的预签名访问 URL
     *
     * @param objectName 对象名称
     * @param expires    过期时间（秒）
     * @return URL 字符串
     */
    String getPresignedUrl(String objectName, int expires) throws Exception;

    /** 检查对象是否存在 */
    boolean exists(String objectName) throws Exception;

    /** 删除对象 */
    void delete(String objectName) throws Exception;

    /**
     * 获取 Bucket 中对象列表（可按前缀过滤）
     *
     * @param prefix    前缀（可为 null）
     * @param recursive 是否递归
     */
    List<String> listObjects(String prefix, boolean recursive) throws Exception;

    /** 获取对象的 Content-Type */
    String getContentType(String objectName) throws Exception;
}
