package com.example.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * 文件儲存服務的接口，定義了文件儲存的相關操作
 */
public interface StorageService {
    /**
     * 初始化文件儲存服務
     */
    void init();

    /**
     * 儲存上傳的檔案
     * @param file 上傳的檔案
     * @return 儲存後的唯一檔案名稱
     */
    String store(MultipartFile file);

    /**
     * 根據檔案名稱載入檔案路徑
     * @param filename 檔案名稱
     * @return 檔案路徑
     */
    Path load(String filename);

    /**
     * 根據檔案名稱載入檔案資源
     * @param filename 檔案名稱
     * @return 檔案資源
     */
    Resource loadAsResource(String filename);

    /**
     * 根據檔案名稱刪除檔案
     * @param filename 檔案名稱
     */
    void delete(String filename);
}
