package com.example.service;

import java.util.List;

import com.example.model.Announcement;

public interface AnnouncementService {
    void save(Announcement announcement);
    Announcement get(Long id);
    List<Announcement> list(int page, int size);
    long count();
    void delete(Long id);
}

