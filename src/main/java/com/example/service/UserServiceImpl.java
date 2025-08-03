package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDao;
import com.example.model.User;

/**
 * 使用者服務的實現，處理使用者的業務邏輯
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    // 自動注入 UserDao，用於數據庫操作
    @Autowired
    private UserDao userDao;

    /**
     * 儲存使用者 (新增或更新)
     * @param user 要儲存的使用者對象
     */
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    /**
     * 獲取所有使用者列表
     * @return 使用者列表
     */
    @Override
    public List<User> list() {
        return userDao.list();
    }
}
