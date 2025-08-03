package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Announcement;
import com.example.service.AnnouncementService;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        int size = 5;
        model.addAttribute("announcements", announcementService.list(page, size));
        long total = announcementService.count();
        int totalPages = (int) Math.ceil((double) total / size);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "announcements";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "announcement_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("announcement") Announcement announcement) {
        announcementService.save(announcement);
        return "redirect:/announcements";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("announcement", announcementService.get(id));
        return "announcement_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        announcementService.delete(id);
        return "redirect:/announcements";
    }
}

