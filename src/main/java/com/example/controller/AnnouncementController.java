package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Announcement;
import com.example.service.AnnouncementService;
import jakarta.validation.Valid;

/**
 * 公告相關的控制器，處理公告的增刪改查等請求
 */
@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    // 自動注入 AnnouncementService，用於處理業務邏輯
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 處理公告列表的請求，支援分頁
     * @param page 當前頁碼，預設為 1
     * @param model 用於傳遞數據到視圖
     * @return 公告列表頁面的視圖名稱
     */
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        int size = 5; // 每頁顯示的公告數量
        // 取得分頁後的公告列表
        model.addAttribute("announcements", announcementService.list(page, size));
        long total = announcementService.count(); // 取得公告總數
        // 計算總頁數
        int totalPages = (int) Math.ceil((double) total / size);
        model.addAttribute("page", page); // 傳遞當前頁碼到視圖
        model.addAttribute("totalPages", totalPages); // 傳遞總頁數到視圖
        return "announcements"; // 返回公告列表頁面的視圖名稱
    }

    /**
     * 處理新增公告的請求，顯示新增公告的表單
     * @param model 用於傳遞數據到視圖
     * @return 新增公告表單頁面的視圖名稱
     */
    @GetMapping("/new")
    public String create(Model model) {
        // 新增一個空的 Announcement 物件到 model，用於表單的數據綁定
        model.addAttribute("announcement", new Announcement());
        return "announcement_form"; // 返回新增公告表單頁面的視圖名稱
    }

    /**
     * 處理儲存公告的請求，包含新增和修改
     * @param announcement 從表單綁定的 Announcement 物件
     * @param bindingResult 用於檢查數據綁定的結果
     * @return 如果成功，則重定向到公告列表頁面；否則返回表單頁面
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("announcement") Announcement announcement, BindingResult bindingResult) {
        // 如果數據綁定有誤 (例如，格式錯誤或驗證失敗)
        if (bindingResult.hasErrors()) {
            // 返回表單頁面，讓使用者修正錯誤
            return "announcement_form";
        }
        // 儲存公告
        announcementService.save(announcement);
        return "redirect:/announcements"; // 重定向到公告列表頁面
    }

    /**
     * 處理修改公告的請求，顯示修改公告的表單
     * @param id 要修改的公告 ID
     * @param model 用於傳遞數據到視圖
     * @return 修改公告表單頁面的視圖名稱
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        // 根據 ID 取得公告物件，並新增到 model
        model.addAttribute("announcement", announcementService.get(id));
        return "announcement_form"; // 返回修改公告表單頁面的視圖名稱
    }

    /**
     * 處理刪除公告的請求
     * @param id 要刪除的公告 ID
     * @return 重定向到公告列表頁面
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // 刪除公告
        announcementService.delete(id);
        return "redirect:/announcements"; // 重定向到公告列表頁面
    }

    /**
     * 處理下載附件的請求
     * @param id 公告 ID
     * @return 包含附件內容的 ResponseEntity
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        // 根據 ID 取得公告物件
        Announcement announcement = announcementService.get(id);
        // 如果公告不存在或沒有附件，則返回 404 Not Found
        if (announcement == null || announcement.getAttachmentFilename() == null) {
            return ResponseEntity.notFound().build();
        }

        // 載入附件資源
        Resource resource = announcementService.loadAsResource(announcement.getAttachmentFilename());
        // 如果資源不存在，則返回 404 Not Found
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 取得原始檔名
        String originalFilename = announcement.getOriginalAttachmentFilename();
        // 返回包含附件內容的 ResponseEntity
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFilename + "\"")
                .body(resource);
    }
}
