package com.bookManager.book.utils;

import java.util.UUID;

/**
 * UUID工具
 * @author ilovejava1314
 * @date 2019/7/20 16:31
 */
public class UuidUtils {
    public static String next(){
        return UUID.randomUUID().toString().replace("-","a");
    }
}
