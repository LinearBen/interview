package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user); // persist 對應 save
    }

    @Override
    public List<User> list() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}
