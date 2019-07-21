package com.bookManager.book.service;

import com.bookManager.book.model.User;
import com.bookManager.book.utils.ConcurrentUtils;
import org.springframework.stereotype.Service;

/**
 * @author ilovejava1314
 * @date 2019/7/21 10:24
 */
@Service
public class HostHolder {

    public User getUser(){
        return ConcurrentUtils.getHost();
    }

    public void setUser(User user){
        ConcurrentUtils.setHost(user);
    }

}
