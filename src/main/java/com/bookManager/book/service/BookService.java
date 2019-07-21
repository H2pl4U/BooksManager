package com.bookManager.book.service;

import com.bookManager.book.dao.BookDao;
import com.bookManager.book.model.Book;
import com.bookManager.book.model.enums.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ilovejava1314
 * @date 2019/7/21 10:05
 */
@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public List<Book> getAllBooks(){
        return bookDao.selectAll();
    }

    public int addBook(Book book){
        return bookDao.addBook(book);
    }

    //放入回收站
    public void deleteBook(int id){
        bookDao.updateBookStatus(id, BookStatusEnum.DELETE.getValue());
    }

    //从回收站恢复
    public void recoverBook(int id){
        bookDao.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
    }
}
