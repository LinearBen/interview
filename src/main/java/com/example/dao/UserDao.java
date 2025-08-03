package com.example.dao;

import java.util.List;

import com.example.model.User;

/**
 * 使用者的數據訪問對象 (DAO) 接口，定義了使用者的數據庫操作
 */
public interface UserDao {
    /**
     * 儲存使用者 (新增或更新)
     * @param user 要儲存的使用者對象
     */
    void save(User user);

    /**
     * 獲取所有使用者列表
     * @return 使用者列表
     */
    List<User> list();
}
