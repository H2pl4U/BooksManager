package com.bookManager.book.biz;

import com.bookManager.book.model.Ticket;
import com.bookManager.book.model.User;
import com.bookManager.book.model.exceptions.LoginRegisterException;
import com.bookManager.book.service.TicketService;
import com.bookManager.book.service.UserService;
import com.bookManager.book.utils.ConcurrentUtils;
import com.bookManager.book.utils.MD5;
import com.bookManager.book.utils.TicketUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ilovejava1314
 * @date 2019/7/21 11:15
 */
@Service
public class LoginBiz {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    /**
     * 登录逻辑，先检查邮箱和密码，然后更新t票。
     * @return 返回最新t票
     * @throws Exception 账号密码错误
     */
    public String login(String email,String password) throws Exception{
        User user = userService.getUserByEmail(email);

        //检查登录信息
        if (user==null)
            throw new LoginRegisterException("邮箱不存在");
        if (!StringUtils.equals(MD5.next(password),user.getPassword()))
            throw new LoginRegisterException("密码不正确");

        //通过uid取出ticket
        Ticket ticket = ticketService.getTicketByUid(user.getId());

        //检查ticket是否存在，没有的话添加
        if(ticket==null){
            ticket = TicketUtils.next(user.getId());
            ticketService.addTicket(ticket);
            return ticket.getTicket();
        }

        //ticket是否过期
        if (ticket.getExpiredAt().before(new Date())){
            ticketService.delTicketByTid(ticket.getId());
            //重新生成ticket
            ticket = TicketUtils.next(user.getId());
            ticketService.addTicket(ticket);
        }
        ConcurrentUtils.setHost(user);
        return ticket.getTicket();

    }

    /**
     * 用户退出登录，只需要删除数据库中用户的t票
     */
    public void logout(String t){
        ticketService.delTicketByT(t);
    }

    public String register(User user) throws Exception{
        //查看邮箱是否存在
        if(userService.getUserByEmail(user.getEmail())!=null){
            throw new LoginRegisterException("用户邮箱已存在");
        }
        //密码MD5加密
        String md5 = MD5.next(user.getPassword());
        user.setPassword(md5);
        userService.addUser(user);

        //生成用户票
        Ticket ticket = TicketUtils.next(user.getId());
        ticketService.addTicket(ticket);

        ConcurrentUtils.setHost(user);
        return ticket.getTicket();
    }



}
