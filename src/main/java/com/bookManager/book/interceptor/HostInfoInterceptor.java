package com.bookManager.book.interceptor;

import com.bookManager.book.model.Ticket;
import com.bookManager.book.model.User;
import com.bookManager.book.service.TicketService;
import com.bookManager.book.service.UserService;
import com.bookManager.book.utils.ConcurrentUtils;
import com.bookManager.book.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author ilovejava1314
 * @date 2019/7/20 16:35
 */
public class HostInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    /**
     * 注入host信息
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String t = CookieUtils.getCookie("t",request);
        if (!StringUtils.isEmpty(t)){
            Ticket ticket = ticketService.getTicketByT(t);
            if (ticket!= null && ticket.getExpiredAt().after(new Date())){
                User host = userService.getUserByUid(ticket.getUserId());
                ConcurrentUtils.setHost(host);
            }
        }

        return true;
    }
}
