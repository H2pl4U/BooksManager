package com.bookManager.book.service;

import com.bookManager.book.dao.TicketDao;
import com.bookManager.book.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ilovejava1314
 * @date 2019/7/21 10:15
 */
@Service
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    public void addTicket(Ticket ticket){
        ticketDao.addTicket(ticket);
    }

    public Ticket getTicketByUid(int uid){
        return ticketDao.selectByUserId(uid);
    }

    public Ticket getTicketByT(String ticket){
        return ticketDao.selectByTicket(ticket);
    }

    public void delTicketByTid(int tid){
        ticketDao.deleteTicketById(tid);
    }

    public void delTicketByT(String ticket){
        ticketDao.deleteTicket(ticket);
    }


}
