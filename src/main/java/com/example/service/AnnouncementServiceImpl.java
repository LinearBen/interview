package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AnnouncementDao;
import com.example.model.Announcement;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public void save(Announcement announcement) {
        announcementDao.save(announcement);
    }

    @Override
    public Announcement get(Long id) {
        return announcementDao.get(id);
    }

    @Override
    public List<Announcement> list(int page, int size) {
        return announcementDao.list(page, size);
    }

    @Override
    public long count() {
        return announcementDao.count();
    }

    @Override
    public void delete(Long id) {
        announcementDao.delete(id);
    }
}

