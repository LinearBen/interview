package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Announcement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * 公告的數據訪問對象 (DAO) 的實現，使用 JPA 進行數據庫操作
 */
@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    // 注入 JPA 的 EntityManager，用於執行數據庫操作
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 儲存公告 (新增或更新)
     * @param announcement 要儲存的公告對象
     */
    @Override
    public void save(Announcement announcement) {
        // 使用 merge 方法，如果 announcement 對象有 ID，則更新；否則新增
        entityManager.merge(announcement);
    }

    /**
     * 根據 ID 獲取公告
     * @param id 公告 ID
     * @return 找到的公告對象，如果不存在則返回 null
     */
    @Override
    public Announcement get(Long id) {
        // 使用 find 方法，根據主鍵 (ID) 查找對象
        return entityManager.find(Announcement.class, id);
    }

    /**
     * 獲取分頁的公告列表
     * @param page 當前頁碼
     * @param size 每頁顯示的數量
     * @return 公告列表
     */
    @Override
    public List<Announcement> list(int page, int size) {
        // 創建查詢，按照發布日期降序排列
        return entityManager.createQuery("from Announcement order by publishDate desc", Announcement.class)
                .setFirstResult((page - 1) * size) // 設置查詢的起始位置
                .setMaxResults(size) // 設置查詢的最大結果數
                .getResultList(); // 獲取結果列表
    }

    /**
     * 獲取公告總數
     * @return 公告總數
     */
    @Override
    public long count() {
        // 創建查詢，計算公告總數
        return entityManager.createQuery("select count(a) from Announcement a", Long.class).getSingleResult();
    }

    /**
     * 根據 ID 刪除公告
     * @param id 公告 ID
     */
    @Override
    public void delete(Long id) {
        // 先根據 ID 獲取公告對象
        Announcement announcement = get(id);
        // 如果公告存在，則刪除
        if (announcement != null) {
            entityManager.remove(announcement);
        }
    }
}
