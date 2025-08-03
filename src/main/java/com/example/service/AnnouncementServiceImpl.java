package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AnnouncementDao;
import com.example.model.Announcement;
import java.util.List;

/**
 * 公告服務的實現，處理公告的業務邏輯
 */
@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    // 自動注入 AnnouncementDao，用於數據庫操作
    @Autowired
    private AnnouncementDao announcementDao;

    // 自動注入 StorageService，用於文件儲存
    @Autowired
    private StorageService storageService;

    /**
     * 儲存公告 (新增或更新)，並處理附件
     * @param announcement 要儲存的公告對象
     */
    @Override
    public void save(Announcement announcement) {
        // 如果是更新操作，先取得舊檔名
        String oldFilename = null;
        if (announcement.getId() != null) {
            Announcement existing = announcementDao.get(announcement.getId());
            if (existing != null) {
                oldFilename = existing.getAttachmentFilename();
            }
        }

        // 如果有新檔案上傳
        if (announcement.getFileUpload() != null && !announcement.getFileUpload().isEmpty()) {
            // 儲存新檔案並設定新檔名
            String newFilename = storageService.store(announcement.getFileUpload());
            announcement.setAttachmentFilename(newFilename);
            // 如果有舊檔案，刪除它
            if (oldFilename != null) {
                storageService.delete(oldFilename);
            }
        } else {
            // 如果是更新操作且沒有上傳新檔案，保留舊檔名
            announcement.setAttachmentFilename(oldFilename);
        }

        // 儲存公告到數據庫
        announcementDao.save(announcement);
    }

    /**
     * 根據 ID 獲取公告
     * @param id 公告 ID
     * @return 找到的公告對象，如果不存在則返回 null
     */
    @Override
    public Announcement get(Long id) {
        return announcementDao.get(id);
    }

    /**
     * 獲取分頁的公告列表
     * @param page 當前頁碼
     * @param size 每頁顯示的數量
     * @return 公告列表
     */
    @Override
    public List<Announcement> list(int page, int size) {
        return announcementDao.list(page, size);
    }

    /**
     * 獲取公告總數
     * @return 公告總數
     */
    @Override
    public long count() {
        return announcementDao.count();
    }

    /**
     * 根據 ID 刪除公告，並刪除相關的附件
     * @param id 公告 ID
     */
    @Override
    public void delete(Long id) {
        // 先從資料庫取得公告資訊，以獲得檔名
        Announcement announcement = announcementDao.get(id);
        if (announcement != null && announcement.getAttachmentFilename() != null) {
            // 刪除對應的檔案
            storageService.delete(announcement.getAttachmentFilename());
        }
        // 從數據庫刪除公告
        announcementDao.delete(id);
    }

    /**
     * 根據檔案名稱載入附件資源
     * @param filename 檔案名稱
     * @return 附件資源
     */
    @Override
    public Resource loadAsResource(String filename) {
        return storageService.loadAsResource(filename);
    }
}
