package com.example.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 文件儲存服務的實現，使用本地文件系統進行儲存
 */
@Service
public class FileSystemStorageService implements StorageService {

    // 從 application.properties 中讀取文件上傳目錄
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 文件儲存的根目錄
    private Path rootLocation;

    /**
     * 初始化文件儲存服務，創建上傳目錄
     */
    @Override
    @PostConstruct
    public void init() {
        this.rootLocation = Paths.get(uploadDir);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    /**
     * 儲存上傳的檔案
     * @param file 上傳的檔案
     * @return 儲存後的唯一檔案名稱
     */
    @Override
    public String store(MultipartFile file) {
        // 清理檔案名稱，防止路徑遍歷攻擊
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        // 產生唯一的檔案名稱，避免檔名衝突
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        try {
            // 如果檔案為空，則拋出異常
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + originalFilename);
            }
            // 安全性檢查，防止檔名中包含 ".."
            if (originalFilename.contains("..")) {
                throw new RuntimeException("Cannot store file with relative path outside current directory " + originalFilename);
            }
            // 將檔案複製到目標位置
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(uniqueFilename), StandardCopyOption.REPLACE_EXISTING);
                return uniqueFilename;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + originalFilename, e);
        }
    }

    /**
     * 根據檔案名稱載入檔案路徑
     * @param filename 檔案名稱
     * @return 檔案路徑
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * 根據檔案名稱載入檔案資源
     * @param filename 檔案名稱
     * @return 檔案資源
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            // 如果資源存在且可讀，則返回資源
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    /**
     * 根據檔案名稱刪除檔案
     * @param filename 檔案名稱
     */
    @Override
    public void delete(String filename) {
        if (filename == null || filename.isEmpty()) return;
        try {
            Path file = load(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            // 記錄錯誤，但不拋出異常，避免事務回滾
            System.err.println("Failed to delete file: " + filename);
        }
    }
}
