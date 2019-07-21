package com.bookManager.book.utils;

import com.bookManager.book.model.User;

/**
 * @author ilovejava1314
 * @date 2019/7/20 16:25
 */
public class ConcurrentUtils {
    private static ThreadLocal<User> host = new ThreadLocal<>();

    public static User getHost() {
        return host.get();
    }

    public static void setHost(User user) {
        host.set(user);
    }
}