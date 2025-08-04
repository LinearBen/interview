package com.example.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

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
            throw new StorageException("無法初始化儲存位置", e);
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
                throw new StorageException("無法儲存空檔案 " + originalFilename);
            }
            // 安全性檢查，防止檔名中包含 ".."
            if (originalFilename.contains("..")) {
                throw new StorageException("無法儲存包含相對路徑的檔案 " + originalFilename);
            }
            // 將檔案複製到目標位置
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(uniqueFilename), StandardCopyOption.REPLACE_EXISTING);
                return uniqueFilename;
            }
        } catch (IOException e) {
            throw new StorageException("儲存檔案失敗 " + originalFilename, e);
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
                throw new StorageFileNotFoundException("讀取檔案失敗: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("讀取檔案失敗: " + filename, e);
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
            // 使用日誌記錄錯誤，而不是拋出異常，以避免在某些情況下（例如，公告刪除成功但附件刪除失敗）導致整個事務回滾。
            logger.warn("刪除檔案失敗: {}", filename, e);
        }
    }
}

/**
 * 自定義儲存異常基類
 */
class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * 自定義檔案未找到異常
 */
class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
