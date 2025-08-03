package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * 使用者的數據訪問對象 (DAO) 的實現，使用 JPA 進行數據庫操作
 */
@Repository
public class UserDaoImpl implements UserDao {

    // 注入 JPA 的 EntityManager，用於執行數據庫操作
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 儲存使用者 (新增)
     * @param user 要儲存的使用者對象
     */
    @Override
    public void save(User user) {
        // 使用 persist 方法，將新的使用者對象持久化到數據庫
        entityManager.persist(user);
    }

    /**
     * 獲取所有使用者列表
     * @return 使用者列表
     */
    @Override
    public List<User> list() {
        // 創建查詢，獲取所有使用者
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}
