package com.bookManager.book.controller;

import com.bookManager.book.biz.LoginBiz;
import com.bookManager.book.model.User;
import com.bookManager.book.service.UserService;
import com.bookManager.book.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ilovejava1314
 * @date 2019/7/21 11:52
 */
@Controller
public class LoginController {
    @Autowired
    private LoginBiz loginBiz;

    @Autowired
    private UserService userService;

    /**
     * 注册页面
     */
    @RequestMapping(path = {"/users/register"},method = RequestMethod.GET)
    public String Register(){
        return "login/register";
    }


    /**
     * 用户注册
     */
    @RequestMapping(path = {"/users/register/do"},method = RequestMethod.POST)
    public String doRegister(
            Model model, HttpServletResponse response,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        try {
            String t = loginBiz.register(user);
            CookieUtils.writeCookie("t",t,response);
            return "redirect:/index";

        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "404";
        }
    }

    /**
     * 登录页面
     */
    @RequestMapping(path = {"/users/login"},method = RequestMethod.GET)
    public String login(){
        return "/login/login";
    }

    /**
     * 用户登录
     */
    @RequestMapping(path = {"/users/login/do"},method = RequestMethod.POST)
    public String doLogin(
            Model model,HttpServletResponse response,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        try {
            String t = loginBiz.login(email,password);
            CookieUtils.writeCookie("t",t,response);
            return "redirect:/index";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "404";
        }
    }

    /**
     * 用户登出
     */
    @RequestMapping(path = {"/users/logout/do"},method = RequestMethod.GET)
    public String doLogout(@CookieValue("t") String t){
        loginBiz.logout(t);
        return "redirect:/index";
    }


}
