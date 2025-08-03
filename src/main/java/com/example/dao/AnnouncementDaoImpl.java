package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Announcement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Announcement announcement) {
        // merge 是 JPA 中對應 Hibernate saveOrUpdate 的方法
        entityManager.merge(announcement);
    }

    @Override
    public Announcement get(Long id) {
        return entityManager.find(Announcement.class, id); // find 對應 get
    }

    @Override
    public List<Announcement> list(int page, int size) {
        return entityManager.createQuery("from Announcement order by publishDate desc", Announcement.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList(); // getResultList 對應 list
    }

    @Override
    public long count() {
        return entityManager.createQuery("select count(a) from Announcement a", Long.class).getSingleResult(); // getSingleResult 對應 uniqueResult
    }

    @Override
    public void delete(Long id) {
        Announcement announcement = get(id);
        if (announcement != null) {
            entityManager.remove(announcement); // remove 對應 delete
        }
    }
}
