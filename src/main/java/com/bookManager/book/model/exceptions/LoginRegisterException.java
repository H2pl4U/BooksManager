package com.bookManager.book.model.exceptions;


/**
 * 注册和登录时异常
 * @author ilovejava1314
 * @date 2019/7/20 16:22
 */
public class LoginRegisterException extends RuntimeException{
    public LoginRegisterException(){
        super();
    }

    public LoginRegisterException(String message){
        super(message);
    }

    public LoginRegisterException(Throwable cause){
        super(cause);
    }
    public LoginRegisterException(String message,Throwable cause){
        super(message,cause);
    }
}
