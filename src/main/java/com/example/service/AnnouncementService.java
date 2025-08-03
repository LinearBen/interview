package com.example.service;

import java.util.List;
import org.springframework.core.io.Resource;

import com.example.model.Announcement;

/**
 * 公告的服務接口，定義了公告的業務邏輯
 */
public interface AnnouncementService {
    /**
     * 儲存公告 (新增或更新)
     * @param announcement 要儲存的公告對象
     */
    void save(Announcement announcement);

    /**
     * 根據 ID 獲取公告
     * @param id 公告 ID
     * @return 找到的公告對象，如果不存在則返回 null
     */
    Announcement get(Long id);

    /**
     * 獲取分頁的公告列表
     * @param page 當前頁碼
     * @param size 每頁顯示的數量
     * @return 公告列表
     */
    List<Announcement> list(int page, int size);

    /**
     * 獲取公告總數
     * @return 公告總數
     */
    long count();

    /**
     * 根據 ID 刪除公告
     * @param id 公告 ID
     */
    void delete(Long id);

    /**
     * 根據檔案名稱載入附件資源
     * @param filename 檔案名稱
     * @return 附件資源
     */
    Resource loadAsResource(String filename);
}
