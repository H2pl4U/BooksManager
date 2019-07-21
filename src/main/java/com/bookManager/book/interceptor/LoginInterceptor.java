package com.bookManager.book.interceptor;

import com.bookManager.book.model.Ticket;
import com.bookManager.book.service.TicketService;
import com.bookManager.book.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author ilovejava1314
 * @date 2019/7/21 10:29
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //无票，去登录
        String t = CookieUtils.getCookie("t",request);
        if(StringUtils.isEmpty(t)){
            response.sendRedirect("/users/login");
            return false;
        }

        //无效票，去登录
        Ticket ticket = ticketService.getTicketByT(t);
        if (ticket==null){
            response.sendRedirect("/users/login");
            return false;
        }

        //过期票，去登录
        if (ticket.getExpiredAt().before(new Date())){
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }
}
