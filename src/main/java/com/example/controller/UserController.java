package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.User;
import com.example.service.UserService;

/**
 * 使用者相關的控制器，處理使用者的增刪改查等請求
 */
@Controller
public class UserController {

    // 自動注入 UserService，用於處理業務邏輯
    @Autowired
    private UserService userService;

    /**
     * 處理首頁的請求
     * @param model 用於傳遞數據到視圖
     * @return 首頁的視圖名稱
     */
    @GetMapping("/")
    public String index(Model model) {
        // 新增一個空的 User 物件到 model，用於表單的數據綁定
        model.addAttribute("user", new User());
        // 取得所有使用者列表，並新增到 model
        model.addAttribute("users", userService.list());
        return "index"; // 返回首頁的視圖名稱
    }

    /**
     * 處理新增使用者的請求
     * @param user 從表單綁定的 User 物件
     * @return 重定向到首頁
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        // 儲存使用者
        userService.save(user);
        return "redirect:/"; // 重定向到首頁
    }
}
