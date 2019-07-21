package com.bookManager.book.service;

import com.bookManager.book.dao.UserDao;
import com.bookManager.book.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ilovejava1314
 * @date 2019/7/21 10:21
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public int addUser(User user){
        return userDao.AddUser(user);
    }

    public User getUserByEmail(String email){
        return userDao.selectByEmail(email);
    }

    public User getUserByUid(int id){
        return userDao.selectById(id);
    }
}
