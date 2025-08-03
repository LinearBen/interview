package com.example.dao;

import java.util.List;

import com.example.model.User;

public interface UserDao {
    void save(User user);
    List<User> list();
}
