package com.example.dao;

import java.util.List;

import com.example.model.Announcement;

/**
 * 公告的數據訪問對象 (DAO) 接口，定義了公告的數據庫操作
 */
public interface AnnouncementDao {
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
}

