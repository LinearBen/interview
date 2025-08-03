package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 公告的實體類，對應數據庫中的 announcements 表
 */
@Entity
@Table(name = "announcements")
public class Announcement {

    // 主鍵，自動增長
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 標題，不可為空，長度不超過 255
    @NotEmpty(message = "標題為必填欄位")
    @Size(max = 255, message = "標題長度不可超過 255 個字元")
    private String title;

    // 發布者，不可為空
    @NotEmpty(message = "發布者為必填欄位")
    private String publisher;

    // 發布日期，不可為空，格式為 yyyy-MM-dd'T'HH:mm
    @NotNull(message = "發布日期為必填欄位")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime publishDate;

    // 截止日期，格式為 yyyy-MM-dd
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    // 內容，不可為空，對應數據庫中的 TEXT 類型
    @Lob
    @NotEmpty(message = "內容為必填欄位")
    @Column(columnDefinition = "TEXT")
    private String content;

    // 附件檔案名稱
    @Column(name = "attachment_filename")
    private String attachmentFilename;

    // 用於接收上傳的檔案，不對應數據庫欄位
    @Transient
    private MultipartFile fileUpload;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentFilename() {
        return attachmentFilename;
    }

    public void setAttachmentFilename(String attachmentFilename) {
        this.attachmentFilename = attachmentFilename;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    // --- ViewModel Methods for JSP ---

    /**
     * 獲取格式化後的發布日期 (yyyy-MM-dd HH:mm)
     * @return 格式化後的發布日期字串
     */
    @Transient
    public String getFormattedPublishDate() {
        if (this.publishDate == null) {
            return "";
        }
        return this.publishDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * 獲取格式化後的截止日期 (yyyy-MM-dd)
     * @return 格式化後的截止日期字串
     */
    @Transient
    public String getFormattedDeadline() {
        if (this.deadline == null) {
            return "";
        }
        return this.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 獲取原始的附件檔案名稱 (不含時間戳記)
     * @return 原始的附件檔案名稱
     */
    @Transient
    public String getOriginalAttachmentFilename() {
        if (this.attachmentFilename == null) {
            return "";
        }
        int underscoreIndex = this.attachmentFilename.indexOf('_');
        if (underscoreIndex != -1) {
            return this.attachmentFilename.substring(underscoreIndex + 1);
        }
        return this.attachmentFilename;
    }
}
